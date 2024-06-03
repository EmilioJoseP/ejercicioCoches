package com.ejerciciocoches.infrastucture.api.dto;

import com.ejerciciocoches.domain.enums.Combustible;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiculoResponseDTO {

    private String matriculaVehiculo;
    private String modeloString;
    private String marca;
    private String color;
    private String fechaMatriculacion;
    private Combustible combustible;

    public String toString() {
        return "Marca: " + marca + " Modelo: " + modeloString + " Matricula: " + matriculaVehiculo + " Color: " + color + " Fecha Matriculacion: " + fechaMatriculacion + " combustible: " + combustible;
    }


    public String toStringPrueba() {
        return "Matricula " + matriculaVehiculo;
    }
}
