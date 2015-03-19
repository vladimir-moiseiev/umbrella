package com.umbrella.model.internal.repository;


import com.umbrella.model.internal.entity.ConnectionInfo;
import org.springframework.data.repository.CrudRepository;

public interface ConnectionInfoRepository extends CrudRepository<ConnectionInfo, Long> {
}
