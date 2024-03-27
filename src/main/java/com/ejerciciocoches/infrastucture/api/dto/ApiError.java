package com.ejerciciocoches.infrastucture.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class ApiError<C> implements Serializable {
    @JsonProperty("app_code")
    private final @NotNull
    @NotEmpty String appCode;
    @JsonProperty("message")
    private final String message;
    @JsonProperty("context")
    private final C context;

    @JsonCreator
    protected ApiError(@JsonProperty("app_code") @NotNull @NotEmpty String appCode, @JsonProperty("message") String message, @JsonProperty("context") C context) {
        this.appCode = appCode;
        this.message = message;
        this.context = context;
    }

    public static <C> ApiError<C> of(@NotNull @NotEmpty String appCode, String message) {
        return new ApiError(appCode, message, (Object)null);
    }

    public static <C> ApiError<C> of(@NotNull @NotEmpty String appCode, String message, C context) {
        return new ApiError(appCode, message, context);
    }

    public String toString() {
        return String.format("ApiError(appCode='%s', message='%s', context='%s')", this.appCode, this.message, this.context);
    }

    public String getAppCode() {
        return this.appCode;
    }

    public String getMessage() {
        return this.message;
    }

    public Object getContext() {
        return this.context;
    }

    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            ApiError<?> apiError = (ApiError)obj;
            return Objects.equals(this.appCode, apiError.appCode) && Objects.equals(this.message, apiError.message);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.appCode, this.message, this.context});
    }
}