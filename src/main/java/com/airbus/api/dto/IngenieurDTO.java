package com.airbus.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class IngenieurDTO {

    private Long id;

    @NotBlank(message = "Le nom de l'ingénieur est obligatoire")
    @Size(max = 255, message = "Le nom ne doit pas dépasser 255 caractères")
    private String nom;

    @NotBlank(message = "La spécialité est obligatoire")
    @Size(max = 255, message = "La spécialité ne doit pas dépasser 255 caractères")
    private String specialite;

    public IngenieurDTO() {}

    public IngenieurDTO(Long id, String nom, String specialite) {
        this.id = id;
        this.nom = nom;
        this.specialite = specialite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
}
