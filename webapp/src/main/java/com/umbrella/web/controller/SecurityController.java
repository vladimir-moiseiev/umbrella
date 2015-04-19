package com.umbrella.web.controller;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.umbrella.model.security.UserProvider;
import com.umbrella.web.controller.dto.UserDTO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.security.Principal;

@Controller
@RequestMapping("/security")
public class SecurityController {

    @Inject
    private UserProvider userProvider;

    @RequestMapping("/getUserDetails")
    @ResponseBody
    public SimpleResponse getUserDetails(Principal principal) {
        String name = principal.getName();
        UserProvider.UserDetails user = userProvider.getUserDetailsByEmail(name);

        return SimpleResponse.create(getUserDTO(user));
    }

    private UserDTO getUserDTO(UserProvider.UserDetails user) {
        return new UserDTO(user.username, user.isAdmin);
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
}
