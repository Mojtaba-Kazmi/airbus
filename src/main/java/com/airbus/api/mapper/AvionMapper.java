package com.airbus.api.mapper;

import com.airbus.api.dto.AvionDTO;
import com.airbus.api.model.Avion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AvionMapper {

    public AvionDTO toDto(Avion avion) {
        return new AvionDTO(
                avion.getId(),
                avion.getModele(),
                avion.getAnnee(),
                avion.getStatut()
        );
    }

    public Avion toEntity(AvionDTO avionDTO) {
        return new Avion(
                avionDTO.getModele(),
                avionDTO.getAnnee(),
                avionDTO.getStatut()
        );
    }

    public List<AvionDTO> toDtoList(List<Avion> avions) {
        return avions.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Avion> toEntityList(List<AvionDTO> avionDTOs) {
        return avionDTOs.stream().map(this::toEntity).collect(Collectors.toList());
    }
}