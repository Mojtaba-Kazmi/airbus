package com.airbus.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.airbus.api.model.Maintenance;
import com.airbus.api.model.StatutMaintenance;
import com.airbus.api.model.TypeMaintenance;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
	
    List<Maintenance> findByAvionId(Long avionId);

    List<Maintenance> findByIngenieurId(Long ingenieurId);

    List<Maintenance> findByStatut(StatutMaintenance statut);
 
    Page<Maintenance> findByStatut(StatutMaintenance statut, Pageable pageable);

    Page<Maintenance> findByType(TypeMaintenance type, Pageable pageable);

    Page<Maintenance> findByStatutAndType(StatutMaintenance statut, TypeMaintenance type, Pageable pageable);
}