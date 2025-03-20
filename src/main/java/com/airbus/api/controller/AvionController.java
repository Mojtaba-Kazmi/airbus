package com.airbus.api.controller;

import com.airbus.api.dto.AvionDTO;
import com.airbus.api.service.AvionService;
import com.airbus.api.service.exception.ResourceNotFoundException;

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

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/avions")
@Tag(name = "Avion", description = "Gestion des avions")
public class AvionController {

    @Autowired
    private AvionService avionService;

    @GetMapping
    @Operation(summary = "Lister tous les avions avec pagination et filtres")
    public ResponseEntity<CollectionModel<EntityModel<AvionDTO>>> getAllAvions(
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) String modele,
            Pageable pageable) {

        Page<AvionDTO> avionPage = avionService.getAllAvions(statut, modele, pageable);
        List<EntityModel<AvionDTO>> avions = avionPage.getContent().stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(avions,
                linkTo(methodOn(AvionController.class).getAllAvions(statut, modele, pageable)).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un avion par ID")
    public ResponseEntity<EntityModel<AvionDTO>> getAvionById(@PathVariable Long id) {
        AvionDTO avionDTO = avionService.getAvionById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avion non trouvé"));

        return ResponseEntity.ok(toModel(avionDTO));
    }

    @PostMapping
    @Operation(summary = "Créer un nouvel avion")
    public ResponseEntity<EntityModel<AvionDTO>> createAvion(@Valid @RequestBody AvionDTO avionDTO) {
        AvionDTO savedAvion = avionService.saveAvion(avionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(savedAvion));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un avion")
    public ResponseEntity<EntityModel<AvionDTO>> updateAvion(@PathVariable Long id, @Valid @RequestBody AvionDTO avionDetails) {
        AvionDTO updatedAvion = avionService.updateAvion(id, avionDetails);
        return ResponseEntity.ok(toModel(updatedAvion));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un avion")
    public void deleteAvion(@PathVariable Long id) {
        avionService.deleteAvion(id);
    }

    private EntityModel<AvionDTO> toModel(AvionDTO avionDTO) {
        return EntityModel.of(avionDTO,
                linkTo(methodOn(AvionController.class).getAvionById(avionDTO.getId())).withSelfRel(),
                linkTo(methodOn(AvionController.class).getAllAvions(null, null, Pageable.unpaged())).withRel("avions"));
    }
}