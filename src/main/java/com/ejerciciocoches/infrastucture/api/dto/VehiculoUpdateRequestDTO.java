package com.ejerciciocoches.infrastucture.api.dto;

import com.ejerciciocoches.domain.enums.Combustible;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiculoUpdateRequestDTO {

    private String matriculaVehiculo;
    private Integer idVehiculo;
    private Integer idModelo;
    private Integer idMarca;
    private String color;
    private String fechaMatriculacion;
    private Combustible combustible;

    public String toString() {
        return "Marca: " + idMarca + " Modelo: " + idModelo + " Matricula: " + matriculaVehiculo + " Color: " + color + " Fecha Matriculacion: " + fechaMatriculacion;
    }
}
