package com.umbrella.web.controller;

import com.umbrella.model.PersonProvider;
import com.umbrella.model.dto.PersonDTO;
import com.umbrella.web.controller.request.SearchRequest;
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

    @Inject
    private PersonProvider personProvider;

    @RequestMapping(value = "/findPersons", method = RequestMethod.POST)
    @ResponseBody
    public SimpleResponse findPersons(@RequestBody SearchRequest request,Principal principal ) {
        List<PersonDTO> persons = personProvider.findPersons(request.getLastName());
        return SimpleResponse.create(persons);
    }
}
