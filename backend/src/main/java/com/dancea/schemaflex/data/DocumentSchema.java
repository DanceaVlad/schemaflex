package com.dancea.schemaflex.data;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DocumentSchema {
    private Integer id;
    private String name;
    private JsonNode dataSchema;
    private JsonNode uiSchema;
}
