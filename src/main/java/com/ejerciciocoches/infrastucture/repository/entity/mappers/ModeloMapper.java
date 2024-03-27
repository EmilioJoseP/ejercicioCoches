package com.ejerciciocoches.infrastucture.repository.entity.mappers;

import com.ejerciciocoches.infrastucture.api.dto.ModeloDTO;
import com.ejerciciocoches.infrastucture.repository.entity.Modelo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ModeloMapper {
    public abstract ModeloDTO modeloToModeloDTO(Modelo modelo);
    public abstract Modelo modeloDTOToModelo(ModeloDTO modeloDTO);
}
