package com.umbrella.model.internal.repository;


import com.umbrella.model.internal.entity.ConnectionInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConnectionInfoRepository extends CrudRepository<ConnectionInfo, Long> {
    List<ConnectionInfo> findByPerson_LastName(String lastname);
}
