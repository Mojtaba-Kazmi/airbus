package com.airbus.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StatutAvion {
    
    @JsonProperty("En service")
    EN_SERVICE,

    @JsonProperty("Hors service")
    HORS_SERVICE,

    @JsonProperty("En réparation")
    EN_REPARATION,

    @JsonProperty("En retrait")
    EN_RETRAIT;
}