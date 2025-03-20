package com.airbus.api.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.airbus.api.dto.IngenieurDTO;
import com.airbus.api.service.IngenieurService;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/ingenieurs")
public class IngenieurController {

	private final IngenieurService ingenieurService;

	public IngenieurController(IngenieurService ingenieurService) {
		this.ingenieurService = ingenieurService;
	}

	@GetMapping
	public CollectionModel<EntityModel<IngenieurDTO>> getAllIngenieurs(Pageable pageable) {
		Page<IngenieurDTO> ingenieurs = ingenieurService.getAllIngenieurs(pageable);
		List<EntityModel<IngenieurDTO>> ingenieurModels = ingenieurs.stream()
				.map(ingenieur -> EntityModel.of(ingenieur,
						linkTo(methodOn(IngenieurController.class).getIngenieurById(ingenieur.getId())).withSelfRel(),
						linkTo(methodOn(IngenieurController.class).getAllIngenieurs(pageable)).withRel("ingenieurs")))
				.collect(Collectors.toList());

		return CollectionModel.of(ingenieurModels,
				linkTo(methodOn(IngenieurController.class).getAllIngenieurs(pageable)).withSelfRel());
	}

	@GetMapping("/{id}")
	public EntityModel<IngenieurDTO> getIngenieurById(@PathVariable Long id) {
		IngenieurDTO ingenieurDTO = ingenieurService.getIngenieurById(id);
		return EntityModel.of(ingenieurDTO,
				linkTo(methodOn(IngenieurController.class).getIngenieurById(id)).withSelfRel(),
				linkTo(methodOn(IngenieurController.class).getAllIngenieurs(Pageable.unpaged())).withRel("ingenieurs"));
	}

	@PostMapping
	public ResponseEntity<EntityModel<IngenieurDTO>> createIngenieur(@Valid @RequestBody IngenieurDTO ingenieurDTO) {
		IngenieurDTO savedIngenieur = ingenieurService.saveIngenieur(ingenieurDTO);
		EntityModel<IngenieurDTO> entityModel = EntityModel.of(savedIngenieur,
				linkTo(methodOn(IngenieurController.class).getIngenieurById(savedIngenieur.getId())).withSelfRel(),
				linkTo(methodOn(IngenieurController.class).getAllIngenieurs(Pageable.unpaged())).withRel("ingenieurs"));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<IngenieurDTO>> updateIngenieur(@PathVariable Long id,
			@Valid @RequestBody IngenieurDTO ingenieurDTO) {
		IngenieurDTO updatedIngenieur = ingenieurService.updateIngenieur(id, ingenieurDTO);
		EntityModel<IngenieurDTO> entityModel = EntityModel.of(updatedIngenieur,
				linkTo(methodOn(IngenieurController.class).getIngenieurById(id)).withSelfRel(),
				linkTo(methodOn(IngenieurController.class).getAllIngenieurs(Pageable.unpaged())).withRel("ingenieurs"));

		return ResponseEntity.ok(entityModel);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteIngenieur(@PathVariable Long id) {
		ingenieurService.deleteIngenieur(id);
		return ResponseEntity.noContent().build();
	}
}