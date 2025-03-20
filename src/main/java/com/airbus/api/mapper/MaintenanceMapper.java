package com.airbus.api.mapper;

import com.airbus.api.dto.MaintenanceDTO;
import com.airbus.api.model.Maintenance;
import com.airbus.api.model.Avion;
import com.airbus.api.model.Ingenieur;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MaintenanceMapper {

    public MaintenanceDTO toDto(Maintenance maintenance) {
        return new MaintenanceDTO(
                maintenance.getId(),
                maintenance.getDate(),
                maintenance.getType(),
                maintenance.getStatut(),
                maintenance.getAvion().getId(),
                maintenance.getIngenieur().getId()
        );
    }

    public Maintenance toEntity(MaintenanceDTO maintenanceDTO) {
        return new Maintenance(
                maintenanceDTO.getDate(),
                maintenanceDTO.getType(),
                maintenanceDTO.getStatut(),
                null,  
                null   
        );
    }

    public Maintenance toEntity(MaintenanceDTO maintenanceDTO, Avion avion, Ingenieur ingenieur) {
        return new Maintenance(
                maintenanceDTO.getDate(),
                maintenanceDTO.getType(),
                maintenanceDTO.getStatut(),
                avion,
                ingenieur
        );
    }

    public List<MaintenanceDTO> toDtoList(List<Maintenance> maintenances) {
        return maintenances.stream().map(this::toDto).collect(Collectors.toList());
    }
}