package com.airbus.api.repository;

import com.airbus.api.model.Avion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface AvionRepository extends JpaRepository<Avion, Long> {

    Page<Avion> findByStatut(String statut, Pageable pageable);

    Page<Avion> findByModeleContainingIgnoreCase(String modele, Pageable pageable);

    Page<Avion> findByStatutAndModeleContainingIgnoreCase(String statut, String modele, Pageable pageable);
}