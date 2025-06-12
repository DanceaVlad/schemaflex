package com.dancea.schemaflex.data;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "_schemas")
public class CustomSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = false, nullable = false, updatable = false)
    private String name;

    @Lob
    @Column(name = "ui_schema", unique = false, columnDefinition = "CLOB", nullable = true, updatable = false)
    private JsonNode uiSchema;

    @Column(name = "data_schema", unique = false, columnDefinition = "CLOB", nullable = false, updatable = false)
    private JsonNode dataSchema;
}
