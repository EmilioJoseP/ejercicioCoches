package com.ejerciciocoches.model.mapper;

import com.ejerciciocoches.model.Modelo;
import com.ejerciciocoches.model.ModeloDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ModeloMapper {
    public abstract ModeloDTO modeloToModeloDTO(Modelo modelo);
    public abstract Modelo modeloDTOToModelo(ModeloDTO modeloDTO);
}
