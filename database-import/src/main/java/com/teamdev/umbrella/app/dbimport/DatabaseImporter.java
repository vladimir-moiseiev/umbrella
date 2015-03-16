package com.teamdev.umbrella.app.dbimport;


import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.teamdev.umbrella.app.KsRow;
import com.teamdev.umbrella.model.internal.entity.City;
import com.teamdev.umbrella.model.internal.entity.Person;
import com.teamdev.umbrella.model.internal.entity.Street;
import com.teamdev.umbrella.model.internal.repository.CityRepository;
import com.teamdev.umbrella.model.internal.repository.PersonRepository;
import com.teamdev.umbrella.model.internal.repository.StreetRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DatabaseImporter {

    @Inject
    private CityRepository cityRepository;

    @Inject
    private StreetRepository streetRepository;

    @Inject
    private PersonRepository personRepository;

    public void importToDatabase(List<KsRow> rows) {

        final Map<City,City> citiesMap = getCityMap();
        final Map<Street,Street> streetsMap = getStreetMap();
        final Map<Person, Person> personMap = getPersonMap();


        for (KsRow row : rows) {

            City city = getCity(citiesMap, row.city);
            Street street = getStreet(streetsMap,row.street);
            Person person = getPerson(personMap, row.lastName, row.firstName, row.secondName, row.phone2, "",
                    null, null, row.building, row.apartment);

//            person.setCity(city);
//            person.setStreet(street);

        }
        //cityRepository.save(map.keySet());
        //streetRepository.save(streetsMap.keySet());

        personRepository.save(personMap.keySet());

    }

    private Map<Person, Person> getPersonMap() {
        Iterable<Person> all = personRepository.findAll();
        int size = Lists.newArrayList(all).size();
        return Maps.newHashMap(Maps.uniqueIndex(all, new Function<Person, Person>() {
            @Override
            public Person apply(Person input) {
                return input;
            }
        }));
    }

    private Map<Street, Street> getStreetMap() {
        return Maps.newHashMap(Maps.uniqueIndex(streetRepository.findAll(), new Function<Street, Street>() {
            @Override
            public Street apply(Street input) {
                return input;
            }
        }));
    }

    private Map<City, City> getCityMap() {
        return Maps.newHashMap(Maps.uniqueIndex(cityRepository.findAll(), new Function<City, City>() {
            @Override
            public City apply(City input) {
                return input;
            }
        }));
    }

    private Person getPerson(final Map<Person, Person> map, String lastName, String firstName, String secondName, String phone,
                             String identificationNumber, City city, Street street, String building, String apartment) {
        Person person = new Person(lastName, firstName, secondName, phone, identificationNumber,
                city, street, building, apartment);

        person.setCity(city);
        person.setStreet(street);

        Person personFromMap = map.get(person);
        if(personFromMap == null) {
            map.put(person,person);
            return person;
        }
        return personFromMap;
    }

    private City getCity(Map<City, City> citiesMap, String cityName) {
        if(cityName == null) {
            return null;
        }
        City city = new City(cityName);
        if(!citiesMap.keySet().contains(city)) {
            //city = cityRepository.save(city);
            citiesMap.put(city,city);
        }
        else {
            city = citiesMap.get(city);
        }
        return city;
    }

    private Street getStreet(Map<Street, Street> map, String name) {
        if(name == null) {
            return null;
        }
        Street street = new Street(name);
        if(!map.keySet().contains(street)) {
            //street = streetRepository.save(street);
            map.put(street,street);
        }
        else {
            street = map.get(street);
        }
        return street;
    }
}
