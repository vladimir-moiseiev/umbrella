package com.teamdev.umbrella.model.internal.repository;


import com.teamdev.umbrella.model.internal.entity.Street;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface StreetRepository extends CrudRepository<Street, Long> {
    Collection<Street> findAll();
}
