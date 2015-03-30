package com.umbrella.model;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.umbrella.model.dto.PersonDTO;
import com.umbrella.model.internal.entity.ConnectionInfo;
import com.umbrella.model.internal.entity.Person;
import com.umbrella.model.internal.entity.Provider;
import com.umbrella.model.internal.repository.ConnectionInfoRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class PersonProvider {

    @Inject
    private ConnectionInfoRepository connectionInfoRepository;

    public List<PersonDTO> findPersons(String name) {
        List<ConnectionInfo> byPerson_lastName = connectionInfoRepository.findByPerson_LastName(name);
        return Lists.newArrayList(getPersons(byPerson_lastName));
    }

    public List<PersonDTO> findPersons(String name, String provider, String city) {
        List<ConnectionInfo> byPerson_lastName = connectionInfoRepository.findByProvider_ProviderAndPerson_City_CityAndPerson_LastName(provider,city,name);
        return Lists.newArrayList(getPersons(byPerson_lastName));
    }

    private List<PersonDTO> getPersons(List<ConnectionInfo> byPerson_lastName) {
        return Lists.transform(byPerson_lastName, new Function<ConnectionInfo, PersonDTO>() {
            @Override
            public PersonDTO apply(ConnectionInfo input) {
                Person person = input.getPerson();
                Provider provider = input.getProvider();
                return new PersonDTO(person.getLastName(), person.getFirstName(), person.getSecondName(), person.getPhone(), person.getIdentificationNumber(),
                        person.getCity().getCity(), person.getStreet().getStreet(), person.getBuilding(), person.getApartment(), provider.getProvider());
            }
        });
    }
}
