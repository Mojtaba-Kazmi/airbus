package com.airbus.api.mapper;

import com.airbus.api.dto.IngenieurDTO;
import com.airbus.api.model.Ingenieur;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IngenieurMapper {

    public IngenieurDTO toDto(Ingenieur ingenieur) {
        return new IngenieurDTO(
                ingenieur.getId(),
                ingenieur.getNom(),
                ingenieur.getSpecialite()
        );
    }

    public Ingenieur toEntity(IngenieurDTO ingenieurDTO) {
        Ingenieur ingenieur = new Ingenieur();
        ingenieur.setNom(ingenieurDTO.getNom());
        ingenieur.setSpecialite(ingenieurDTO.getSpecialite());
        return ingenieur;
    }

    public List<IngenieurDTO> toDtoList(List<Ingenieur> ingenieurs) {
        return ingenieurs.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Ingenieur> toEntityList(List<IngenieurDTO> ingenieurDTOs) {
        return ingenieurDTOs.stream().map(this::toEntity).collect(Collectors.toList());
    }
}