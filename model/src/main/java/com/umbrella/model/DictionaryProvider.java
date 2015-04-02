package com.umbrella.model;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.umbrella.model.dto.CityDTO;
import com.umbrella.model.dto.ProviderDTO;
import com.umbrella.model.internal.entity.City;
import com.umbrella.model.internal.entity.Provider;
import com.umbrella.model.internal.repository.CityRepository;
import com.umbrella.model.internal.repository.ProviderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DictionaryProvider {
    @Inject
    private ProviderRepository providerRepository;

    @Inject
    private CityRepository cityRepository;

    public List<CityDTO> getCities() {
        final Collection<City> all = cityRepository.findAll();
        return Lists.newArrayList(Collections2.transform(all, new Function<City, CityDTO>() {
            @Override
            public CityDTO apply(City input) {
                return new CityDTO(input.getId(), input.getCity());
            }
        }));

    }

    public List<ProviderDTO> getProviders(){
        final List<Provider> all = providerRepository.findAll();
        return Lists.newArrayList(Lists.transform(all, new Function<Provider, ProviderDTO>() {
            @Override
            public ProviderDTO apply(Provider input) {
                return new ProviderDTO(input.getId(), input.getProvider());
            }
        }));
    }
}
