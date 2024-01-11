package com.ejerciciocoches.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModeloDTO {

    private long idModelo;
    private String nombreModelo;
    private MarcaDTO marcaModelo;
}
