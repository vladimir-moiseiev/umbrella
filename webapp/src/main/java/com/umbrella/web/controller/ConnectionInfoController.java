package com.umbrella.web.controller;


import com.umbrella.model.ConnectionInfoManager;
import com.umbrella.model.ConnectionInfoManagerImpl;
import com.umbrella.web.controller.request.CommentRequest;
import com.umbrella.web.security.ExtendedUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;
import java.security.Principal;

@Controller
@RequestMapping("/info")
public class ConnectionInfoController {

    @Inject
    ConnectionInfoManager connectionInfoManager;

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addComment( @RequestBody CommentRequest request,Principal principal ) {

        connectionInfoManager.addComment(ExtendedUser.getUserId (principal), request.getRecord(), request.getText());

    }
}
