package com.umbrella.model;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.umbrella.model.dto.CommentDTO;
import com.umbrella.model.dto.PersonDTO;
import com.umbrella.model.internal.DataCreator;
import com.umbrella.model.internal.entity.*;
import com.umbrella.model.internal.repository.ConnectionInfoRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Service
//@Transactional(readOnly = true)
public class PersonProvider {

    private Logger LOG = Logger.getLogger(PersonProvider.class);

    @Inject
    private ConnectionInfoRepository connectionInfoRepository;

    public List<PersonDTO> findPersons(String name) {
        LOG.info("searching for: " + name);
        List<ConnectionInfo> byPerson_lastName = connectionInfoRepository.findByPerson_LastName(name);
        return Lists.newArrayList(getPersons(byPerson_lastName));
    }

    public List<PersonDTO> findPersons(String name, String provider, String city) {
        LOG.info("searching for: " + name + " in city: " + city );
        List<ConnectionInfo> byPerson_lastName = connectionInfoRepository.findByProvider_ProviderAndPerson_City_CityAndPerson_LastName(provider,city,name);
        return Lists.newArrayList(getPersons(byPerson_lastName));
    }

    public List<PersonDTO> findPersons(String name, String city, boolean triolan, boolean ks, boolean volya) {
        LOG.info("searching for: " + name + " in city: " + city + ", providers: " + triolan + " " + ks + " " + volya + " ");

        List<ConnectionInfo> result = Lists.newLinkedList();
        if(ks) {
            result.addAll(connectionInfoRepository.findPerson(DataCreator.KYIVSTAR, city, name));
        }
        if(triolan) {
            result.addAll(connectionInfoRepository.findPerson(DataCreator.TRIOLAN, city, name));
        }
        if(volya) {
            result.addAll(connectionInfoRepository.findPerson(DataCreator.VOLYA, city, name));
        }
        LOG.info("searching result: " + result.size() + " items.");
        return Lists.newArrayList(getPersons(result));
    }

    private List<PersonDTO> getPersons(List<ConnectionInfo> byPerson_lastName) {
        return Lists.transform(byPerson_lastName, new Function<ConnectionInfo, PersonDTO>() {
            @Override
            public PersonDTO apply(ConnectionInfo input) {
                Person person = input.getPerson();
                Provider provider = input.getProvider();
                Set<String> phones = Sets.newHashSet(Collections2.transform(person.getPhones(), new Function<Phone, String>() {
                    @Override
                    public String apply(Phone input) {
                        return input.getPhone();
                    }
                }));

                List<CommentDTO> comments = Lists.newArrayList( Lists.transform(input.getComments(), new Function<Comment, CommentDTO>() {
                    @Override
                    public CommentDTO apply(Comment input) {
                        return new CommentDTO(input.getId(), "",input.getDate(),input.getText() );
                    }
                }));
                return new PersonDTO(person.getId(), person.getLastName(), person.getFirstName(), person.getSecondName(), phones, person.getIdentificationNumber(),
                        person.getCity().getCity(), person.getStreet().getStreet(), person.getBuilding(), person.getApartment(), provider.getProvider(),comments);
            }
        });
    }
}
