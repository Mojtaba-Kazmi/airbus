package com.airbus.api.service;

import com.airbus.api.dto.AvionDTO;
import com.airbus.api.mapper.AvionMapper;
import com.airbus.api.model.Avion;
import com.airbus.api.repository.AvionRepository;
import com.airbus.api.service.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class AvionService {
    private static final Logger logger = LoggerFactory.getLogger(AvionService.class);

    @Autowired
    private AvionRepository avionRepository;

    @Autowired
    private AvionMapper avionMapper; 

    public Page<AvionDTO> getAllAvions(String statut, String modele, Pageable pageable) {
        Page<Avion> avions;

        if (statut != null && modele != null) {
            avions = avionRepository.findByStatutAndModeleContainingIgnoreCase(statut, modele, pageable);
        } else if (statut != null) {
            avions = avionRepository.findByStatut(statut, pageable);
        } else if (modele != null) {
            avions = avionRepository.findByModeleContainingIgnoreCase(modele, pageable);
        } else {
            avions = avionRepository.findAll(pageable);
        }

        return avions.map(avionMapper::toDto); // On applique le mapper après
    }

    public Optional<AvionDTO> getAvionById(Long id) {
        return avionRepository.findById(id).map(avionMapper::toDto);
    }

    @Transactional
    public AvionDTO saveAvion(@Valid AvionDTO avionDTO) {
        Avion avion = avionMapper.toEntity(avionDTO);
        Avion savedAvion = avionRepository.save(avion);
        logger.info("Ajout d'un nouvel avion : {}", savedAvion.getModele());
        return avionMapper.toDto(savedAvion);
    }

    @Transactional
    public AvionDTO updateAvion(Long id, @Valid AvionDTO avionDTO) {
        Avion avion = avionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avion non trouvé"));

        avion.setModele(avionDTO.getModele());
        avion.setStatut(avionDTO.getStatut());

        logger.info("Mise à jour de l'avion {} : {}", id, avionDTO.getModele());
        return avionMapper.toDto(avionRepository.save(avion));
    }

    public void deleteAvion(Long id) {
        if (!avionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Avion non trouvé");
        }
        avionRepository.deleteById(id);
        logger.warn("Suppression de l'avion avec l'ID: {}", id);
    }
}