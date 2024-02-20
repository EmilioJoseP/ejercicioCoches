package com.ejerciciocoches.repository.entity;

import com.ejerciciocoches.model.Combustible;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="vehiculos")
public class Vehiculo {

    @Id
    @Column(name = "id_vehiculo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVehiculo;

    @Column(name = "matricula_vehiculo", unique = true)
    private String matriculaVehiculo;

    @Column(name = "pintura")
    private String pintura;

    @Column(name = "fecha_matriculacion")
    private Date fechaMatriculacion;

    @JoinColumn(name = "id_modelo")
    @ManyToOne
    private Modelo modelo;

    @Column(name = "combustible")
    @Enumerated(EnumType.STRING)
    private Combustible combustible;
}
