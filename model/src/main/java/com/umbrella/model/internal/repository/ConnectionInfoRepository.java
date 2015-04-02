package com.umbrella.model.internal.repository;


import com.umbrella.model.internal.entity.ConnectionInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConnectionInfoRepository extends CrudRepository<ConnectionInfo, Long> {
    List<ConnectionInfo> findByPerson_LastName(String lastname);
    List<ConnectionInfo> findByPerson_City_City(String city);
    List<ConnectionInfo> findByProvider_Provider(String provider);
    List<ConnectionInfo> findByProvider_ProviderAndPerson_City_CityAndPerson_LastName(String provider, String city, String lastName);

    @Query("select c from ConnectionInfo as c " +
            "where (c.person.firstName like CONCAT('%',:name,'%') or c.person.secondName like CONCAT('%',:name,'%') or c.person.lastName like CONCAT('%',:name,'%')) and " +
            "c.provider.provider = :provider and c.person.city.city = :city")
    List<ConnectionInfo> findPerson(@Param("provider") String provider, @Param("city") String city, @Param("name") String name);
}
