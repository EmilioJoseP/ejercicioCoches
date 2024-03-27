package com.ejerciciocoches.domain;

import lombok.*;

@Getter
@Setter
public class ModeloDomain {
    private int idModelo;
    private String nombreModelo;
    private MarcaDomain marcaDomain;
}
