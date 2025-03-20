package com.airbus.api.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.airbus.api.dto.MaintenanceDTO;
import com.airbus.api.mapper.MaintenanceMapper;
import com.airbus.api.model.Maintenance;
import com.airbus.api.model.StatutMaintenance;
import com.airbus.api.model.TypeMaintenance;
import com.airbus.api.service.MaintenanceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/maintenances")
@Tag(name = "Maintenance", description = "Gestion des opérations de maintenance")
public class MaintenanceController {
    
    private static final Logger logger = LoggerFactory.getLogger(MaintenanceController.class);

    @Autowired
    private MaintenanceService maintenanceService;

    @Autowired
    private MaintenanceMapper maintenanceMapper;

    @GetMapping
    @Operation(summary = "Lister toutes les maintenances avec pagination et filtres")
    public ResponseEntity<CollectionModel<EntityModel<MaintenanceDTO>>> getAllMaintenances(
            @RequestParam(required = false) StatutMaintenance statut,
            @RequestParam(required = false) TypeMaintenance type,
            Pageable pageable) {

        Page<MaintenanceDTO> maintenancePage = maintenanceService.getAllMaintenances(statut, type, pageable)
                .map(maintenanceMapper::toDto);

        List<EntityModel<MaintenanceDTO>> maintenances = maintenancePage.getContent().stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(maintenances,
                linkTo(methodOn(MaintenanceController.class).getAllMaintenances(statut, type, pageable)).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une maintenance par ID")
    public ResponseEntity<EntityModel<MaintenanceDTO>> getMaintenanceById(@PathVariable Long id) {
        MaintenanceDTO maintenanceDTO = maintenanceService.getMaintenanceById(id)
                .map(maintenanceMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Maintenance non trouvée"));

        return ResponseEntity.ok(toModel(maintenanceDTO));
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle maintenance")
    public ResponseEntity<EntityModel<MaintenanceDTO>> createMaintenance(@Valid @RequestBody MaintenanceDTO maintenanceDTO) {
        logger.info("Requête reçue pour création de maintenance: {}", maintenanceDTO);

        Maintenance savedMaintenance = maintenanceService.saveMaintenance(maintenanceDTO);  // Pass DTO to service

        MaintenanceDTO savedMaintenanceDTO = maintenanceMapper.toDto(savedMaintenance);

        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(savedMaintenanceDTO));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Mise à jour partielle d'une maintenance")
    public ResponseEntity<EntityModel<MaintenanceDTO>> updateMaintenance(@PathVariable Long id, @RequestBody MaintenanceDTO maintenanceDTO) {
        // Convert the DTO to the entity
        Maintenance maintenanceEntity = maintenanceMapper.toEntity(maintenanceDTO);
        
        // Call the service to update the maintenance
        Maintenance updatedMaintenance = maintenanceService.updatePartial(id, maintenanceEntity);
        
        // Convert the updated entity back to DTO
        MaintenanceDTO updatedMaintenanceDTO = maintenanceMapper.toDto(updatedMaintenance);
        
        // Return the updated maintenance DTO as a response
        return ResponseEntity.ok(toModel(updatedMaintenanceDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer une maintenance")
    public void deleteMaintenance(@PathVariable Long id) {
        maintenanceService.deleteMaintenance(id);
    }

    @PutMapping("/{id}/assign/{ingenieurId}")
    @Operation(summary = "Assigner une maintenance à un ingénieur")
    public ResponseEntity<EntityModel<MaintenanceDTO>> assignMaintenance(@PathVariable Long id, @PathVariable Long ingenieurId) {
        MaintenanceDTO assignedMaintenanceDTO = maintenanceMapper.toDto(
                maintenanceService.assignMaintenance(id, ingenieurId)
        );
        return ResponseEntity.ok(toModel(assignedMaintenanceDTO));
    }

    @PutMapping("/{id}/close")
    @Operation(summary = "Clôturer une maintenance en cours")
    public ResponseEntity<EntityModel<MaintenanceDTO>> closeMaintenance(@PathVariable Long id) {
        MaintenanceDTO closedMaintenanceDTO = maintenanceMapper.toDto(
                maintenanceService.closeMaintenance(id)
        );
        return ResponseEntity.ok(toModel(closedMaintenanceDTO));
    }

    private EntityModel<MaintenanceDTO> toModel(MaintenanceDTO maintenanceDTO) {
        return EntityModel.of(maintenanceDTO,
                linkTo(methodOn(MaintenanceController.class).getMaintenanceById(maintenanceDTO.getId())).withSelfRel(),
                linkTo(methodOn(MaintenanceController.class).getAllMaintenances(null, null, Pageable.unpaged())).withRel("maintenances"));
    }
}