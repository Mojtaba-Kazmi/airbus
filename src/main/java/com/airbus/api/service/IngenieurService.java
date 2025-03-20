package com.airbus.api.service;

import com.airbus.api.dto.IngenieurDTO;
import com.airbus.api.mapper.IngenieurMapper;
import com.airbus.api.model.Ingenieur;
import com.airbus.api.repository.IngenieurRepository;
import com.airbus.api.service.exception.ResourceNotFoundException;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IngenieurService {

    private static final Logger logger = LoggerFactory.getLogger(IngenieurService.class);
    private final IngenieurRepository ingenieurRepository;
    private final IngenieurMapper ingenieurMapper;

    public IngenieurService(IngenieurRepository ingenieurRepository, IngenieurMapper ingenieurMapper) {
        this.ingenieurRepository = ingenieurRepository;
        this.ingenieurMapper = ingenieurMapper;
    }

    public Page<IngenieurDTO> getAllIngenieurs(Pageable pageable) {
        return ingenieurRepository.findAll(pageable)
                .map(ingenieurMapper::toDto);
    }

    public IngenieurDTO getIngenieurById(Long id) {
        Ingenieur ingenieur = ingenieurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingénieur non trouvé avec l'ID: " + id));
        return ingenieurMapper.toDto(ingenieur);
    }

    public IngenieurDTO saveIngenieur(@Valid IngenieurDTO ingenieurDTO) {
        if (ingenieurRepository.findByNom(ingenieurDTO.getNom()).isPresent()) {
            throw new IllegalArgumentException("Un ingénieur avec ce nom existe déjà");
        }
        Ingenieur ingenieur = ingenieurMapper.toEntity(ingenieurDTO);
        Ingenieur savedIngenieur = ingenieurRepository.save(ingenieur);
        logger.info("Ajout d'un nouvel ingénieur: {}", savedIngenieur.getNom());
        return ingenieurMapper.toDto(savedIngenieur);
    }

    public IngenieurDTO updateIngenieur(Long id, @Valid IngenieurDTO ingenieurDTO) {
        Ingenieur ingenieur = ingenieurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingénieur non trouvé avec l'ID: " + id));
        
        ingenieur.setNom(ingenieurDTO.getNom());
        ingenieur.setSpecialite(ingenieurDTO.getSpecialite());

        Ingenieur updatedIngenieur = ingenieurRepository.save(ingenieur);
        logger.info("Mise à jour de l'ingénieur ID {}: {}", id, updatedIngenieur.getNom());
        return ingenieurMapper.toDto(updatedIngenieur);
    }

    public void deleteIngenieur(Long id) {
        if (!ingenieurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ingénieur non trouvé avec l'ID: " + id);
        }
        ingenieurRepository.deleteById(id);
        logger.warn("Suppression de l'ingénieur avec l'ID: {}", id);
    }
}