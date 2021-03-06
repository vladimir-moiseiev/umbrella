package com.umbrella.model.internal.repository;

import com.umbrella.model.internal.entity.City;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface CityRepository extends CrudRepository<City, Long> {
    Collection<City> findAll();
}
