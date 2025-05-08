package com.dancea.schemaflex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dancea.schemaflex.data.Schema;

public interface SchemaRepository extends JpaRepository<Schema, Integer> {
}
