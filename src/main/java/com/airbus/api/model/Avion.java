package com.airbus.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Audited
@Table(name = "avions")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Avion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le modèle est obligatoire")
    @Column(name = "modele", nullable = false, length = 255)
    private String modele;

    @NotNull(message = "L'année est obligatoire")
    @Min(value = 1900, message = "L'année doit être supérieure à 1900")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "annee", nullable = false)
    private Integer annee;

    @NotNull(message = "Le statut est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 50)
    private StatutAvion statut;

    @JsonIgnore 
    @OneToMany(mappedBy = "avion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Maintenance> maintenances = new ArrayList<>();

    public Avion() {}

    public Avion(String modele, Integer annee, StatutAvion statut) {
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
        Avion avion = (Avion) o;
        return Objects.equals(id, avion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Avion{" +
                "id=" + id +
                ", modele='" + modele + '\'' +
                ", annee=" + annee +
                ", statut=" + statut +
                '}';
    }
}