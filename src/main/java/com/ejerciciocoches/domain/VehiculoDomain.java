package com.ejerciciocoches.domain;

import com.ejerciciocoches.domain.enums.Combustible;
import lombok.*;
import java.util.Date;

@Getter
@Setter
public class VehiculoDomain {
    private int idVehiculo;
    private String matriculaVehiculo;
    private String pintura;
    private Date fechaMatriculacion;
    private ModeloDomain modeloDomain;
    private Combustible combustible;
}
