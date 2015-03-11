package com.teamdev.umbrella.model.repository;


import com.teamdev.umbrella.model.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
