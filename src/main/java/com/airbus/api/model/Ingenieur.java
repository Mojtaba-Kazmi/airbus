package com.airbus.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.List;
import java.util.Objects;

@Entity
@Audited
@Table(name = "ingenieurs")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Ingenieur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de l'ingénieur est obligatoire")
    @Size(max = 255, message = "Le nom ne doit pas dépasser 255 caractères")
    @Column(name = "nom", nullable = false, length = 255)
    private String nom;

    @NotBlank(message = "La spécialité est obligatoire")
    @Size(max = 255, message = "La spécialité ne doit pas dépasser 255 caractères")
    @Column(name = "specialite", nullable = false, length = 255)
    private String specialite;

    @JsonIgnore
    @OneToMany(mappedBy = "ingenieur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Maintenance> maintenances;

    public Ingenieur() {}

    public Ingenieur(String nom, String specialite) {
        this.nom = nom;
        this.specialite = specialite;
    }

    public Long getId() {
        return id;
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

    public List<Maintenance> getMaintenances() {
        return maintenances;
    }

    public void setMaintenances(List<Maintenance> maintenances) {
        this.maintenances = maintenances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingenieur ingenieur = (Ingenieur) o;
        return Objects.equals(id, ingenieur.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ingenieur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", specialite='" + specialite + '\'' +
                '}';
    }
}