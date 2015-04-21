package com.umbrella.web.controller;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.umbrella.model.security.UserProvider;
import com.umbrella.web.controller.dto.UserDTO;
import com.umbrella.web.controller.request.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.security.Principal;

@Controller
@RequestMapping("/security")
public class SecurityController {

    @Inject
    private UserProvider userProvider;

    @Inject
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("/getUserDetails")
    @ResponseBody
    public SimpleResponse getUserDetails(Principal principal) {
        String name = principal.getName();
        UserProvider.UserDetails user = userProvider.getUserDetailsByEmail(name);

        return SimpleResponse.create(getUserDTO(user));
    }

    private UserDTO getUserDTO(UserProvider.UserDetails user) {
        return new UserDTO(user.id, user.username, user.validUntil, user.isAdmin);
    }

    @RequestMapping("/getUsers")
    @ResponseBody
    public SimpleResponse getUsers(Principal principal) {
        String name = principal.getName();
        UserProvider.UserDetails user = userProvider.getUserDetailsByEmail(name);
        if(!user.isAdmin) {
            throw new AccessDeniedException("Only admin allowed to perform this operation");
        }

        return SimpleResponse.create(Lists.newArrayList(Lists.transform(userProvider.getAllUsers(), new Function<UserProvider.UserDetails, UserDTO>() {
            @Override
            public UserDTO apply(UserProvider.UserDetails input) {
                return getUserDTO(input);
            }
        })));
    }

    @RequestMapping(value = "/setUser", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void setUser(@RequestBody UserRequest userRequest, Principal principal) {
        String name = principal.getName();
        UserProvider.UserDetails user = userProvider.getUserDetailsByEmail(name);
        if(!user.isAdmin) {
            throw new AccessDeniedException("Only admin allowed to perform this operation");
        }

        if(userRequest.getUserId() == 0) {
            userProvider.createUser(userRequest.getUsername(), bCryptPasswordEncoder.encode(userRequest.getPassword()),userRequest.isAdmin(),userRequest.getValidUntil());
        } else {
            if(userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                userProvider.setPassword(userRequest.getUserId(), bCryptPasswordEncoder.encode(userRequest.getPassword()));
            }
            if(userRequest.getValidUntil() != null) {
                userProvider.setValidUntil(userRequest.getUserId(), userRequest.getValidUntil());
            }
        }

    }

    @RequestMapping(value = "/removeUser", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void removeUser(@RequestParam("id") long id, Principal principal) {
        String name = principal.getName();
        UserProvider.UserDetails user = userProvider.getUserDetailsByEmail(name);
        if(!user.isAdmin) {
            throw new AccessDeniedException("Only admin allowed to perform this operation");
        }

        userProvider.removeUser(id);
    }
}
