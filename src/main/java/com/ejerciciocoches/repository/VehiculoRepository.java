package com.ejerciciocoches.repository;

import com.ejerciciocoches.repository.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    List<Vehiculo> findAll();

    Vehiculo findByMatriculaVehiculo(String matriculaVehiculo);

    Vehiculo findByidVehiculo(int idVehiculo);

    @Query(value = "SELECT v.matricula_vehiculo, v.id_vehiculo, v.id_modelo, v.pintura, v.fecha_matriculacion, v.combustible " +
            "FROM vehiculos v " +
            "LEFT JOIN modelos mo ON v.id_modelo = mo.id_modelo " +
            "LEFT JOIN marcas ma ON mo.id_marca = ma.id_marca " +
            "WHERE ma.id_marca = :marca", nativeQuery = true)
    List<Vehiculo> findByMarca(int marca);

}
