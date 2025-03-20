package com.airbus.api.dto;

import com.airbus.api.model.StatutMaintenance;
import com.airbus.api.model.TypeMaintenance;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class MaintenanceDTO {

    private Long id;

    @NotNull(message = "La date de maintenance est obligatoire")
    private LocalDate date;

    @NotNull(message = "Le type de maintenance est obligatoire")
    private TypeMaintenance type;

    @NotNull(message = "Le statut de maintenance est obligatoire")
    private StatutMaintenance statut;

    @NotNull(message = "L'avion est obligatoire")
    private Long avionId;

    @NotNull(message = "L'ingénieur est obligatoire")
    private Long ingenieurId;

    public MaintenanceDTO() {
    }

    public MaintenanceDTO(Long id, LocalDate date, TypeMaintenance type, StatutMaintenance statut, Long avionId, Long ingenieurId) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.statut = statut;
        this.avionId = avionId;
        this.ingenieurId = ingenieurId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getAvionId() {
        return avionId;
    }

    public void setAvionId(Long avionId) {
        this.avionId = avionId;
    }

    public Long getIngenieurId() {
        return ingenieurId;
    }

    public void setIngenieurId(Long ingenieurId) {
        this.ingenieurId = ingenieurId;
    }
}