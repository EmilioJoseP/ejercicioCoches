package com.ejerciciocoches.application.service;

import com.ejerciciocoches.domain.MarcaDomain;
import com.ejerciciocoches.domain.VehiculoDomain;
import com.ejerciciocoches.domain.mappers.MarcaDomainMapper;
import com.ejerciciocoches.infrastucture.repository.entity.Marca;
import com.ejerciciocoches.infrastucture.repository.MarcaRepository;
import com.ejerciciocoches.infrastucture.repository.entity.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarcaService {

    @Autowired
    MarcaRepository marcaRepository;

    @Autowired
    MarcaDomainMapper marcaDomainMapper;

    public Marca findByIdMarca(int idMarca) {
        return marcaRepository.findByIdMarca(idMarca);
    }

    public String findNombreByIdMarca(int idMarca) {
        return marcaRepository.findByIdMarca(idMarca).getNombreMarca();
    }

    public boolean existeIdMarca(int idMarca) {
        boolean existe = false;
        if (marcaRepository.findByIdMarca(idMarca) != null) {
            existe = true;
        }

        return existe;
    }

    public Marca convertToMarcaInfras(MarcaDomain marcaDomain) {
        return marcaDomainMapper.marcaDomainToMarcaInfra(marcaDomain);
    }

    public MarcaDomain convertToMarcaDomain(Marca marca) {
        return marcaDomainMapper.marcaInfraToMarcaDomain(marca);
    }
}
