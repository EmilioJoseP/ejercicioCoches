package com.ejerciciocoches.domain.enums;

import com.ejerciciocoches.domain.exceptions.DomainException;
import org.springframework.util.StringUtils;

public enum Combustible {
    GASOLINA,
    DIESEL,
    ELECTRICO;

    public static Combustible of(String combustible) {
        try {
            if(!StringUtils.hasText(combustible)) {
                return Combustible.valueOf(combustible.toUpperCase());
            }
            return null;
        } catch (IllegalArgumentException e) {
            throw new DomainException("Tipo de combusitble invalido: " + combustible);
        }
    }
}
