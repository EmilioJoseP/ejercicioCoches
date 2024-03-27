package com.ejerciciocoches.infrastucture.repository;

import com.ejerciciocoches.infrastucture.repository.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

    List<Marca> findAll();

    Marca findByIdMarca(int idMarca);
}
