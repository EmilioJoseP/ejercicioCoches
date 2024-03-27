package com.ejerciciocoches.infrastucture.repository.entity.mappers;

import com.ejerciciocoches.infrastucture.api.dto.MarcaDTO;
import com.ejerciciocoches.infrastucture.repository.entity.Marca;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class MarcaMapper {
    @Mapping(target = "idMarca", source = "idMarca")
    public abstract MarcaDTO marcaToMarcaDTO(Marca marca);

    @Mapping(target = "idMarca", source = "idMarca")
    public abstract Marca marcaDTOToMarca(MarcaDTO marcaDTO);

}
