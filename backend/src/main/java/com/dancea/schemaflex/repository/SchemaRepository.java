package com.dancea.schemaflex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dancea.schemaflex.data.CustomSchema;

public interface SchemaRepository extends JpaRepository<CustomSchema, Integer> {
}
