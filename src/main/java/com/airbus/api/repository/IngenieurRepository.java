package com.airbus.api.repository;

import com.airbus.api.model.Ingenieur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface IngenieurRepository extends JpaRepository<Ingenieur, Long> {

    Page<Ingenieur> findBySpecialiteContainingIgnoreCase(String specialite, Pageable pageable);

    Optional<Ingenieur> findByNom(String nom);
}