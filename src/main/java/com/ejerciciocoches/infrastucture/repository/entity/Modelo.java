package com.ejerciciocoches.infrastucture.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="modelos")
public class Modelo {

    @Id
    @Column(name = "id_modelo")
    private int idModelo;

    @Column(name = "nombre_modelo")
    private String nombreModelo;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;
}
