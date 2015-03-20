package com.umbrella.web.controller;

import com.umbrella.model.security.UserProvider;
import com.umbrella.web.controller.dto.UserDTO;
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

        return SimpleResponse.create(new UserDTO(user.username, user.isAdmin));
    }
}
