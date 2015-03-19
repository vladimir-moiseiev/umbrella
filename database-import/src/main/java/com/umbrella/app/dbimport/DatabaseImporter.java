package com.umbrella.app.dbimport;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.umbrella.app.KsRow;
import com.umbrella.app.TriolanRow;
import com.umbrella.app.VolyaRow;
import com.umbrella.model.internal.entity.*;
import com.umbrella.model.internal.repository.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

//todo: phones list in entity

@Service
public class DatabaseImporter {

    public static final long KYIVSTAR_ID = 3L;
    public static final long TRIOLAN_ID = 1L;
    public static final long VOLYA_ID = 2L;

    @Inject
    private CityRepository cityRepository;

    @Inject
    private StreetRepository streetRepository;

    @Inject
    private PersonRepository personRepository;

    @Inject
    private ProviderRepository providerRepository;

    @Inject
    private ConnectionInfoRepository connectionInfoRepository;

    private Map<City, City> citiesMap;
    private Map<Street, Street> streetsMap;
    private Map<Person, Person> personMap;
    private Map<ConnectionInfo, ConnectionInfo> connectionMap;

    public DatabaseImporter() {

    }

    public void initFromDb() {
        citiesMap = getCityMap();
        streetsMap = getStreetMap();
        personMap = getPersonMap();
        connectionMap = getConnectionMap();
    }

    public void importKsToDatabase(List<KsRow> rows) {
        Provider kyivstar = providerRepository.findOne(KYIVSTAR_ID);

        for (KsRow row : rows) {

            City city = getCity(citiesMap, row.city);
            Street street = getStreet(streetsMap,row.street);
            Person person = getPerson(personMap, row.lastName, row.firstName, row.secondName, row.phone2, "",
                    city, street, row.building, row.apartment);


            ConnectionInfo connectionInfo = getConnection(connectionMap,person,kyivstar);
        }
        //personRepository.save(personMap.keySet());
        connectionInfoRepository.save(connectionMap.keySet());
    }

    public void importTriolanToDatabase(List<TriolanRow> rows) {
        Provider triolan = providerRepository.findOne(TRIOLAN_ID);
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

            ConnectionInfo connectionInfo = getConnection(connectionMap,person,triolan);
        }
        //personRepository.save(personMap.keySet());
        connectionInfoRepository.save(connectionMap.keySet());

    }

    public void importVolyaToDatabase(List<VolyaRow> rows) {
        Provider volya = providerRepository.findOne(VOLYA_ID);
        for (VolyaRow row : rows) {


            List<String> fioList = parseFio(row);
            if(fioList.size() == 0) {
                continue;
            }
            String lastName = fioList.get(0);
            String firstName = fioList.size() > 1 ? fioList.get(1) : "";
            String secondName = fioList.size() > 2 ?fioList.get(2) : "";


            String city = row.getCity();
            String street = row.getStreet();
            String building = row.getBuilding();
            String apartment = row.getApartment();
            List<String> phones = parsePhones(row);

            City cityEntity = getCity(citiesMap, city);
            Street streetEntity = getStreet(streetsMap,street);
            Person person = getPerson(personMap, lastName, firstName, secondName, phones.size() > 0 ? phones.get(0) : "", "",
                    cityEntity, streetEntity, building, apartment);

            ConnectionInfo connectionInfo = getConnection(connectionMap,person,volya);

        }

        //personRepository.save(personMap.keySet());
        connectionInfoRepository.save(connectionMap.keySet());

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

    private List<String> parseFio(VolyaRow row) {
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

    private List<String> parsePhones(VolyaRow row) {
        String phones = row.getPhones();
        String[] split = phones.split("\\D");
        return Lists.newArrayList(Collections2.filter(Lists.newArrayList(split), new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return !input.isEmpty();
            }
        }));
    }

    private Map<ConnectionInfo,ConnectionInfo> getConnectionMap() {
        Iterable<ConnectionInfo> all = connectionInfoRepository.findAll();
        return Maps.newHashMap(Maps.uniqueIndex(all, new Function<ConnectionInfo, ConnectionInfo>() {
            @Override
            public ConnectionInfo apply(ConnectionInfo input) {
                return input;
            }
        }));
    }

    private Map<Person, Person> getPersonMap() {
        Iterable<Person> all = personRepository.findAll();
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

    private ConnectionInfo getConnection(Map<ConnectionInfo, ConnectionInfo> map, Person person, Provider kyivstar) {
        ConnectionInfo connectionInfo = new ConnectionInfo();
        connectionInfo.setProvider(kyivstar);
        connectionInfo.setPerson(person);

        ConnectionInfo connectionInfoFromMap = map.get(connectionInfo);
        if(connectionInfoFromMap == null) {
            map.put(connectionInfo,connectionInfo);
            return connectionInfo;
        }
        return connectionInfoFromMap;
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
