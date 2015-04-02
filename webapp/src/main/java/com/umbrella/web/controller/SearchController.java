package com.umbrella.web.controller;

import com.umbrella.model.PersonProvider;
import com.umbrella.model.dto.PersonDTO;
import com.umbrella.web.controller.request.SearchRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    private Logger LOG = Logger.getLogger(SearchController.class);
    @Inject
    private PersonProvider personProvider;

    @RequestMapping(value = "/findPersons", method = RequestMethod.POST)
    @ResponseBody
    public SimpleResponse findPersons(@RequestBody SearchRequest request, Principal principal ) {
        LOG.info("requesting " + request.getLastName() + " in " + request.getCity());
        if(request.getCity().isEmpty() && request.isKs() && request.isTriolan() && request.isVolya()) {
            List<PersonDTO> persons = personProvider.findPersons(request.getLastName());
            LOG.info("returning 1 - " + persons.size());
            return SimpleResponse.create(persons);
        }
        else {
            List<PersonDTO> persons = personProvider.findPersons(request.getLastName(),request.getCity(), request.isTriolan(), request.isKs(), request.isVolya());
            LOG.info("returning 2 - " + persons.size());
            return SimpleResponse.create(persons);
        }
    }
}
