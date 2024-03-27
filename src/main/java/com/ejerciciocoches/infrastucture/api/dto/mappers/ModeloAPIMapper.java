package com.ejerciciocoches.infrastucture.api.dto.mappers;

import com.ejerciciocoches.infrastucture.repository.entity.Modelo;
import com.ejerciciocoches.infrastucture.api.dto.ModeloDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ModeloAPIMapper {
    public abstract ModeloDTO modeloToModeloDTO(Modelo modelo);
    public abstract Modelo modeloDTOToModelo(ModeloDTO modeloDTO);
}
