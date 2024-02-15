package com.ejerciciocoches.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiculoRequestDTO {

    private String matriculaVehiculo;
    private Integer idModelo;
    private Integer idMarca;
    private String color;
    private String fechaMatriculacion;
    private Combustible combustible;

    public String toString() {
        return "Marca: " + idMarca + " Modelo: " + idModelo + " Matricula: " + matriculaVehiculo + " Color: " + color + " Fecha Matriculacion: " + fechaMatriculacion;
    }
}
