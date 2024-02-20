package com.ejerciciocoches.repository;

import com.ejerciciocoches.repository.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    List<Modelo> findAll();

    //List<Modelo> findByNombreModelo(String nombreModelo);

    Modelo findByNombreModelo(String nombreModelo);

    Modelo findByIdModelo(int idModelo);
}
