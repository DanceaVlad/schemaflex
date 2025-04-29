package com.dancea.schemaflex.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeDocumentRequest {
    private Integer id;
    private String data;
}
