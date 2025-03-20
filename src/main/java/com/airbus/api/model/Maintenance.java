package com.airbus.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Audited
@Table(name = "maintenances")
@JsonIgnoreProperties(ignoreUnknown = true) 
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date de maintenance est obligatoire")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull(message = "Le type de maintenance est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private TypeMaintenance type;

    @NotNull(message = "Le statut de maintenance est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 50)
    private StatutMaintenance statut;

    @ManyToOne
    @JoinColumn(name = "avion_id", nullable = false)
    @NotNull(message = "L'avion est obligatoire")
    private Avion avion;

    @ManyToOne 
    @JoinColumn(name = "ingenieur_id", nullable = false)
    @NotNull(message = "L'ingénieur est obligatoire")
    private Ingenieur ingenieur;

    public Maintenance() {}

    public Maintenance(LocalDate date, TypeMaintenance type, StatutMaintenance statut, Avion avion, Ingenieur ingenieur) {
        this.date = date;
        this.type = type;
        this.statut = statut;
        this.avion = avion;
        this.ingenieur = ingenieur;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public TypeMaintenance getType() {
        return type;
    }

    public void setType(TypeMaintenance type) {
        this.type = type;
    }

    public StatutMaintenance getStatut() {
        return statut;
    }

    public void setStatut(StatutMaintenance statut) {
        this.statut = statut;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public Ingenieur getIngenieur() {
        return ingenieur;
    }

    public void setIngenieur(Ingenieur ingenieur) {
        this.ingenieur = ingenieur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maintenance that = (Maintenance) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "id=" + id +
                ", date=" + date +
                ", type=" + type +
                ", statut=" + statut +
                ", avion=" + avion +
                ", ingenieur=" + ingenieur +
                '}';
    }
}