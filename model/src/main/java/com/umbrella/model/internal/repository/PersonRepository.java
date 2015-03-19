package com.umbrella.model.internal.repository;


import com.umbrella.model.internal.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
