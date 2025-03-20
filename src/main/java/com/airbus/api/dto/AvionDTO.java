package com.airbus.api.dto;

import com.airbus.api.model.StatutAvion;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AvionDTO {
	private Long id;

	@NotBlank(message = "Le modèle est obligatoire")
	private String modele;

	@NotNull(message = "L'année est obligatoire")
	@Min(value = 1900, message = "L'année doit être supérieure à 1900")
	private Integer annee;

	@NotNull(message = "Le statut est obligatoire")
	private StatutAvion statut;

	public AvionDTO() {
	}

	public AvionDTO(Long id, String modele, Integer annee, StatutAvion statut) {
		this.id = id;
		this.modele = modele;
		this.annee = annee;
		this.statut = statut;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModele() {
		return modele;
	}

	public void setModele(String modele) {
		this.modele = modele;
	}

	public Integer getAnnee() {
		return annee;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	public StatutAvion getStatut() {
		return statut;
	}

	public void setStatut(StatutAvion statut) {
		this.statut = statut;
	}
}