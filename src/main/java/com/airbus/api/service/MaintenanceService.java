package com.airbus.api.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airbus.api.dto.MaintenanceDTO;
import com.airbus.api.mapper.MaintenanceMapper;
import com.airbus.api.model.Avion;
import com.airbus.api.model.Ingenieur;
import com.airbus.api.model.Maintenance;
import com.airbus.api.model.StatutMaintenance;
import com.airbus.api.model.TypeMaintenance;
import com.airbus.api.repository.AvionRepository;
import com.airbus.api.repository.IngenieurRepository;
import com.airbus.api.repository.MaintenanceRepository;
import com.airbus.api.service.exception.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MaintenanceService {
    private static final Logger logger = LoggerFactory.getLogger(MaintenanceService.class);
    
    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private IngenieurRepository ingenieurRepository;

    @Autowired
    private AvionRepository avionRepository;

    @Autowired
    private MaintenanceMapper maintenanceMapper;

    public Page<Maintenance> getAllMaintenances(StatutMaintenance statut, TypeMaintenance type, Pageable pageable) {
        if (statut != null && type != null) {
            return maintenanceRepository.findByStatutAndType(statut, type, pageable);
        } else if (statut != null) {
            return maintenanceRepository.findByStatut(statut, pageable);
        } else if (type != null) {
            return maintenanceRepository.findByType(type, pageable);
        }
        return maintenanceRepository.findAll(pageable);
    }

    public Optional<Maintenance> getMaintenanceById(Long id) {
        return maintenanceRepository.findById(id);
    }

    public List<Maintenance> getMaintenancesByAvionId(Long avionId) {
        return maintenanceRepository.findByAvionId(avionId);
    }

    public List<Maintenance> getMaintenancesByIngenieurId(Long ingenieurId) {
        return maintenanceRepository.findByIngenieurId(ingenieurId);
    }

    @Transactional
    public Maintenance saveMaintenance(MaintenanceDTO maintenanceDTO) {
        if (maintenanceDTO.getAvionId() == null) {
            throw new IllegalArgumentException("L'ID de l'avion est obligatoire pour créer une maintenance.");
        }

        Avion avion = avionRepository.findById(maintenanceDTO.getAvionId())
                .orElseThrow(() -> new ResourceNotFoundException("Avion non trouvé avec l'ID: " + maintenanceDTO.getAvionId()));

        Ingenieur ingenieur = ingenieurRepository.findById(maintenanceDTO.getIngenieurId())
                .orElseThrow(() -> new ResourceNotFoundException("Ingénieur non trouvé avec l'ID: " + maintenanceDTO.getIngenieurId()));

        Maintenance maintenance = maintenanceMapper.toEntity(maintenanceDTO, avion, ingenieur);
        
        logger.info("Ajout d'une nouvelle maintenance pour l'avion {}", avion.getId());
        return maintenanceRepository.save(maintenance);
    }

    @Transactional
    public Maintenance updatePartial(Long id, Maintenance maintenanceDetails) {
        
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance non trouvée"));

        if (maintenanceDetails.getDate() != null) {
            maintenance.setDate(maintenanceDetails.getDate());
        }
        if (maintenanceDetails.getStatut() != null) {
            maintenance.setStatut(maintenanceDetails.getStatut());
        }
        if (maintenanceDetails.getAvion() != null) {
            Avion avion = avionRepository.findById(maintenanceDetails.getAvion().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Avion non trouvé avec l'ID: " + maintenanceDetails.getAvion().getId()));
            maintenance.setAvion(avion);
        }
        if (maintenanceDetails.getIngenieur() != null) {
            Ingenieur ingenieur = ingenieurRepository.findById(maintenanceDetails.getIngenieur().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ingénieur non trouvé avec l'ID: " + maintenanceDetails.getIngenieur().getId()));
            maintenance.setIngenieur(ingenieur);
        }

        logger.info("Mise à jour partielle de la maintenance {}", id);
        return maintenanceRepository.save(maintenance);
    }

    public void deleteMaintenance(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance non trouvée avec l'ID: " + id);
        }
        logger.warn("Suppression de la maintenance avec l'ID: {}", id);
        maintenanceRepository.deleteById(id);
    }

    @Transactional
    public Maintenance assignMaintenance(Long maintenanceId, Long ingenieurId) {
        Maintenance maintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance non trouvée avec l'ID: " + maintenanceId));
        Ingenieur ingenieur = ingenieurRepository.findById(ingenieurId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingénieur non trouvé avec l'ID: " + ingenieurId));

        if (maintenance.getType() == TypeMaintenance.REPARATION && !"Mécanique".equalsIgnoreCase(ingenieur.getSpecialite())) {
            throw new IllegalArgumentException("L'ingénieur ne peut pas être assigné à ce type de maintenance");
        }

        maintenance.setIngenieur(ingenieur);
        maintenance.setStatut(StatutMaintenance.EN_COURS);
        logger.info("Maintenance {} assignée à l'ingénieur {}", maintenanceId, ingenieurId);
        return maintenanceRepository.save(maintenance);
    }

    @Transactional
    public Maintenance closeMaintenance(Long maintenanceId) {
        Maintenance maintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance non trouvée avec l'ID: " + maintenanceId));

        if (!maintenance.getStatut().equals(StatutMaintenance.EN_COURS)) {
            throw new IllegalStateException("Seules les maintenances en cours peuvent être clôturées");
        }

        maintenance.setStatut(StatutMaintenance.TERMINEE);
        logger.info("Maintenance {} clôturée avec succès", maintenanceId);
        return maintenanceRepository.save(maintenance);
    }
}