package com.ejerciciocoches.infrastucture.repository;

import com.ejerciciocoches.application.service.VehiculoService;
import com.ejerciciocoches.domain.MarcaDomain;
import com.ejerciciocoches.infrastucture.repository.entity.Marca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public abstract class MarcaRepositoryImpl implements MarcaRepository {

    @Autowired
    VehiculoService vehiculoService;

    /*public List<MarcaDomain> findByIdMarcaDomain(int idMarca) {
        List<Marca> listMarcas = findByIdMarca(idMarca);
        return listMarcas.stream().map(vehiculoService.convertToVehiculoDomain()).toList();
    }*/
}
