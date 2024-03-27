package com.ejerciciocoches.domain.mappers;

import com.ejerciciocoches.domain.MarcaDomain;
import com.ejerciciocoches.domain.VehiculoDomain;
import com.ejerciciocoches.infrastucture.api.dto.MarcaDTO;
import com.ejerciciocoches.infrastucture.repository.entity.Marca;
import com.ejerciciocoches.infrastucture.repository.entity.Vehiculo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class MarcaDomainMapper {
    @Mapping(target = "idMarca", source = "idMarca")
    public abstract MarcaDTO marcaToMarcaDTO(Marca marca);

    @Mapping(target = "idMarca", source = "idMarca")
    public abstract Marca marcaDTOToMarca(MarcaDTO marcaDTO);

    public abstract Marca marcaDomainToMarcaInfra(MarcaDomain marcaDomain);

    public abstract MarcaDomain marcaInfraToMarcaDomain(Marca marca);

}
