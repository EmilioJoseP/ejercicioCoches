package com.ejerciciocoches.model;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="marcas")
public class Marca {

    @Id
    @Column(name = "id_marca")
    private Long idMarca;

    @Column(name = "nombre_marca")
    private String nombreMarca;

    @Column(name = "pais_origen_marca")
    private String paisOrigenMarca;

    /*@OneToMany
    @JoinColumn(name = "id_modelos")
    private List<Modelos> modelos;*/

    //Si estan los dos puestos peta. Imagino que es infinito se llama uno a otro sin parar
}
