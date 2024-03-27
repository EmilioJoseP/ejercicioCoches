package com.ejerciciocoches.infrastucture.repository;

import com.ejerciciocoches.application.service.VehiculoService;
import com.ejerciciocoches.domain.VehiculoDomain;
import com.ejerciciocoches.domain.mappers.VehiculoDomainMapper;
import com.ejerciciocoches.infrastucture.repository.entity.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public abstract class VehiculoRepositoryImpl implements VehiculoRepository {

    private final VehiculoDomainMapper vehiculoDomainMapper;

    public VehiculoRepositoryImpl(VehiculoDomainMapper vehiculoDomainMapper) {
        this.vehiculoDomainMapper = vehiculoDomainMapper;
    }

    public List<VehiculoDomain> findByMarcaDomain(int idMarca) {
        List<Vehiculo> listMarcas = findByMarca(idMarca);
        return listMarcas.stream().map(this::convertToVehiculoDomain).toList();
    }

    public List<VehiculoDomain> findAllDomain() {
        List<Vehiculo> listMarcas = findAll();
        return listMarcas.stream().map(this::convertToVehiculoDomain).toList();
    }

    public Vehiculo convertToVehiculoInfras(VehiculoDomain vehiculo) {
        return vehiculoDomainMapper.vehiculoDomainToVehiculoInfra(vehiculo);
    }

    public VehiculoDomain convertToVehiculoDomain(Vehiculo vehiculo) {
        return vehiculoDomainMapper.vehiculoInfraToVehiculoDomain(vehiculo);
    }
}
