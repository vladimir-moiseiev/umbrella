package com.teamdev.umbrella.app.dbimport;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.teamdev.umbrella.app.KsRow;
import com.teamdev.umbrella.app.TriolanRow;
import com.teamdev.umbrella.model.internal.entity.City;
import com.teamdev.umbrella.model.internal.entity.Person;
import com.teamdev.umbrella.model.internal.entity.Street;
import com.teamdev.umbrella.model.internal.repository.CityRepository;
import com.teamdev.umbrella.model.internal.repository.PersonRepository;
import com.teamdev.umbrella.model.internal.repository.StreetRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
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

    public void importKsToDatabase(List<KsRow> rows) {

        final Map<City,City> citiesMap = getCityMap();
        final Map<Street,Street> streetsMap = getStreetMap();
        final Map<Person, Person> personMap = getPersonMap();


        for (KsRow row : rows) {

            City city = getCity(citiesMap, row.city);
            Street street = getStreet(streetsMap,row.street);
            Person person = getPerson(personMap, row.lastName, row.firstName, row.secondName, row.phone2, "",
                    city, street, row.building, row.apartment);

        }
        personRepository.save(personMap.keySet());

    }

    public void importTriolanToDatabase(List<TriolanRow> rows) {

        final Map<City,City> citiesMap = getCityMap();
        final Map<Street,Street> streetsMap = getStreetMap();
        final Map<Person, Person> personMap = getPersonMap();


        for (TriolanRow row : rows) {


            List<String> fioList = parseFio(row);
            if(fioList.size() == 0) {
                continue;
            }
            String lastName = fioList.get(0);
            String firstName = fioList.size() > 1 ? fioList.get(1) : "";
            String secondName = fioList.size() > 2 ?fioList.get(2) : "";


            List<String> addressList = parseAddress(row);
            String city = addressList.get(0);
            String street = addressList.get(1);
            String building = addressList.get(2);
            String apartment = addressList.get(3);
            List<String> phones = parsePhones(row);

            City cityEntity = getCity(citiesMap, city);
            Street streetEntity = getStreet(streetsMap,street);
            Person person = getPerson(personMap, lastName, firstName, secondName, phones.size() > 0 ? phones.get(0) : "", "",
                    cityEntity, streetEntity, building, apartment);

            int x = 1;

        }
        personRepository.save(personMap.keySet());

    }
    private List<String> parseFio(TriolanRow row) {
        List<String> result = Lists.newArrayListWithCapacity(3);

        String fio = row.getFio();
        String[] split = fio.split("[, \\.]");
        if(split.length > 0) {
            result.add(split[0]);
        }
        if(split.length > 1) {
            result.add(split[1]);
        }
        if(split.length > 2) {
            result.add(split[2]);
        }
        return result;
    }

    private List<String> parseAddress(TriolanRow row) {
        List<String> result = Lists.newArrayListWithCapacity(4);

        String address = row.getAddress();
        String[] split = address.split("[,]");
        String citySplit = split[0].trim();
        result.add((citySplit.contains("г.") ? citySplit.substring(2) : citySplit).trim());

        result.add(split[1].trim());

        String buildingSplit = split[2].trim();
        result.add((buildingSplit.contains("д.") ? buildingSplit.substring(2) : buildingSplit).trim());

        String apartmentSplit = split[3].trim();
        result.add((apartmentSplit.contains("кв.") ? apartmentSplit.substring(3) : apartmentSplit).trim());

        return result;
    }

    private List<String> parsePhones(TriolanRow row) {
        String phones = row.getPhones();
        String[] split = phones.split("\\D");
        return Lists.newArrayList(Collections2.filter(Lists.newArrayList(split), new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return !input.isEmpty();
            }
        }));
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
