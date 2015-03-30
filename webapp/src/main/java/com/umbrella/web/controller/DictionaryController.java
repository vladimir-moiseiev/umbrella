package com.umbrella.web.controller;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.umbrella.model.DictionaryProvider;
import com.umbrella.model.internal.entity.City;
import com.umbrella.model.internal.entity.Provider;
import com.umbrella.model.internal.repository.CityRepository;
import com.umbrella.model.internal.repository.ProviderRepository;
import com.umbrella.web.controller.request.SearchRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/data")
public class DictionaryController {

    @Inject
    private DictionaryProvider dictionaryProvider;

    @RequestMapping(value = "/getCities", method = RequestMethod.GET)
    @ResponseBody
    public SimpleResponse getCities(Principal principal ) {


        return SimpleResponse.create(dictionaryProvider.getCities());
    }

    @RequestMapping(value = "/getProviders", method = RequestMethod.GET)
    @ResponseBody
    public SimpleResponse getProviders(Principal principal ) {
        return SimpleResponse.create(dictionaryProvider.getProviders());
    }
}
