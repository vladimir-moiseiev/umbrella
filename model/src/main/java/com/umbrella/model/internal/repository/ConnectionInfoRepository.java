package com.umbrella.model.internal.repository;


import com.umbrella.model.internal.entity.ConnectionInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConnectionInfoRepository extends CrudRepository<ConnectionInfo, Long> {
    List<ConnectionInfo> findByPerson_LastName(String lastname);
    List<ConnectionInfo> findByPerson_City_City(String city);
    List<ConnectionInfo> findByProvider_Provider(String provider);
    List<ConnectionInfo> findByProvider_ProviderAndPerson_City_CityAndPerson_LastName(String provider, String city, String lastName);
}
