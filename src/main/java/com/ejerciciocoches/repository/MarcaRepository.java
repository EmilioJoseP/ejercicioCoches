package com.ejerciciocoches.repository;

import com.ejerciciocoches.model.Marca;
import com.ejerciciocoches.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

    List<Marca> findAll();

    Marca findByIdMarca(int idMarca);
}
