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

    private static Logger LOG = Logger.getLogger(SearchController.class);
    private static Logger USAGE_LOG = Logger.getLogger("usageLog");
    @Inject
    private PersonProvider personProvider;

    @RequestMapping(value = "/findPersons", method = RequestMethod.POST)
    @ResponseBody
    public SimpleResponse findPersons(@RequestBody SearchRequest request, Principal principal ) {
        LOG.info("requesting " + request.getLastName() + " in " + request.getCity());

        USAGE_LOG.info("[" + principal.getName() + "] - " + buildLogString(request));

        if(request.getCity().isEmpty() && request.isKs() && request.isTriolan() && request.isVolya()) {
            List<PersonDTO> persons = personProvider.findPersons(request.getLastName());
            LOG.debug("returning 1 - " + persons.size());
            return SimpleResponse.create(persons);
        }
        else {
            List<PersonDTO> persons = personProvider.findPersons(request.getLastName(),request.getCity(), request.isTriolan(), request.isKs(), request.isVolya());
            LOG.debug("returning 2 - " + persons.size());
            return SimpleResponse.create(persons);
        }
    }

    private String buildLogString(SearchRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(" ФИО: \'" + request.getLastName() + "\'");

        builder.append(", город: \'");
        builder.append( request.getCity().isEmpty() ? "-" : request.getCity() );
        builder.append("\'");

        builder.append(", провайдер: \'");

        builder.append( request.isKs() ? "Киевстар |" : "-|");
        builder.append(request.isTriolan() ? "Триолан" : "-");
        builder.append(request.isVolya() ? "|Воля" : "|-");

        builder.append("\'");
        return builder.toString();
    }
}
