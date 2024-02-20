package com.ejerciciocoches.model.mapper;

import com.ejerciciocoches.repository.entity.Marca;
import com.ejerciciocoches.model.MarcaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class MarcaMapper {
    @Mapping(target = "idMarca", source = "idMarca")
    public abstract MarcaDTO marcaToMarcaDTO(Marca marca);

    @Mapping(target = "idMarca", source = "idMarca")
    public abstract Marca marcaDTOToMarca(MarcaDTO marcaDTO);

}
