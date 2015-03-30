package com.umbrella.model.internal.repository;


import com.umbrella.model.internal.entity.Provider;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProviderRepository extends CrudRepository<Provider, Long> {
    List<Provider> findAll();
}
