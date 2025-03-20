package com.airbus.api.repository;

import com.airbus.api.model.Revinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevinfoRepository extends JpaRepository<Revinfo, Integer> {

}