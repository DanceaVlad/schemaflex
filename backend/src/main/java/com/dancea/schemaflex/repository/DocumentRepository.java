package com.dancea.schemaflex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dancea.schemaflex.data.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

}
