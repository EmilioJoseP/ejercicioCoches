package com.ejerciciocoches.domain.mappers;

import com.ejerciciocoches.domain.ModeloDomain;
import com.ejerciciocoches.infrastucture.api.dto.ModeloDTO;
import com.ejerciciocoches.infrastucture.repository.entity.Modelo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ModeloDomainMapper {
    public abstract ModeloDTO modeloToModeloDTO(Modelo modelo);
    public abstract Modelo modeloDTOToModelo(ModeloDTO modeloDTO);

    public abstract Modelo modeloDomainToModeloInfra(ModeloDomain modeloDomain);

    public abstract ModeloDomain modeloInfraToModeloDomain(Modelo modelo);
}
