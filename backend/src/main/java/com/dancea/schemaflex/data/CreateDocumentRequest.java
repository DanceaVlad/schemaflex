package com.dancea.schemaflex.data;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDocumentRequest {
    private String name;
    private Integer schemaId;
    private JsonNode data;
}
