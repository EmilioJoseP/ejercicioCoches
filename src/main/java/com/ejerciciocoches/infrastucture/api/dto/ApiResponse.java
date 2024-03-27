package com.ejerciciocoches.infrastucture.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<C> {
    @JsonProperty("content")
    private final C content;

    @JsonCreator
    protected ApiResponse(@JsonProperty("content") C content) {
        this.content = content;
    }

    public static <C> ApiResponse<C> of(C content) {
        return new ApiResponse(content);
    }

    public C getContent() {
        return this.content;
    }

    public String toString() {
        return "ApiResponse(" + this.content + ")";
    }
}
