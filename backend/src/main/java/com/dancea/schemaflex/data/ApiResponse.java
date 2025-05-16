package com.dancea.schemaflex.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponse<T> {
    private boolean success;
    private ErrorBody[] errors;
    private T data;
}
