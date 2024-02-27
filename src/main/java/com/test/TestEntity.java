package com.test;

import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.persistence.Column;

import java.time.Instant;
import java.util.UUID;

@MappedEntity("test")
public class TestEntity {

    @Id
    @AutoPopulated
    private UUID id;

    @Column
    @DateCreated
    private Instant dateProcessed;

    public TestEntity() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(Instant dateProcessed) {
        this.dateProcessed = dateProcessed;
    }
}