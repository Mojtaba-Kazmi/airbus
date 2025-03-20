package com.airbus.api.model;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import jakarta.persistence.*;

@Entity
@RevisionEntity
@Table(name = "revinfo")
public class Revinfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    @Column(name = "rev", nullable = false, updatable = false)
    private Integer rev;

    @RevisionTimestamp
    @Column(name = "revtstmp", nullable = false)
    private Long revtstmp; 

    public Revinfo() {}

    public Integer getRev() {
        return rev;
    }

    public void setRev(Integer rev) {
        this.rev = rev;
    }

    public Long getRevtstmp() {
        return revtstmp;
    }

    public void setRevtstmp(Long revtstmp) {
        this.revtstmp = revtstmp;
    }

    @Override
    public String toString() {
        return "Revinfo{" +
                "rev=" + rev +
                ", revtstmp=" + revtstmp +
                '}';
    }
}