package com.ejerciciocoches.domain.mappers;

import com.ejerciciocoches.domain.VehiculoDomain;
import com.ejerciciocoches.infrastucture.api.dto.VehiculoRequestDTO;
import com.ejerciciocoches.infrastucture.api.dto.VehiculoResponseDTO;
import com.ejerciciocoches.infrastucture.repository.entity.Vehiculo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface VehiculoDomainMapper {

    public VehiculoDomainMapper INSTANCE = Mappers.getMapper(VehiculoDomainMapper.class);

    @Mapping(target = "color", source = "pintura")
    @Mapping(target = "fechaMatriculacion", source = "fechaMatriculacion", qualifiedByName = "fechaToString")
    @Mapping(target = "modeloString", source = "modeloDomain.nombreModelo")
    @Mapping(target = "marca", source = "modeloDomain.marcaDomain.nombreMarca")
    @Mapping(target = "combustible", source = "combustible")
    public abstract VehiculoResponseDTO vehiculoToVehiculoResponseDTO(VehiculoDomain vehiculo);

    @Mapping(target = "pintura", source = "color")
    @Mapping(target = "fechaMatriculacion", source = "fechaMatriculacion", qualifiedByName = "stringToFecha")
    @Mapping(target = "modeloDomain.idModelo", source = "idModelo")
    @Mapping(target = "modeloDomain.marcaDomain.idMarca", source = "idMarca")
    @Mapping(target = "combustible", source = "combustible")
    //@Mapping(target = "combustible", source = "stado")
    public abstract VehiculoDomain vehiculoRequestDTOToVehiculoDomain(VehiculoRequestDTO vehiculoRequestDTO);//String stado);

    @Mapping(target = "modelo", source = "modeloDomain")
    @Mapping(target = "modelo.marca", source = "modeloDomain.marcaDomain")
    public abstract Vehiculo vehiculoDomainToVehiculoInfra(VehiculoDomain vehiculoDomain);

    @Mapping(target = "modeloDomain", source = "modelo")
    @Mapping(target = "modeloDomain.marcaDomain", source = "modelo.marca")
    public abstract VehiculoDomain vehiculoInfraToVehiculoDomain(Vehiculo vehiculo);


    //Conversiones manuales
    @Named("fechaToString")
    default public String fechaToString(Date fecha) throws ParseException {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        String fechaString = formatoFecha.format(fecha).toString();
        return fechaString;
    }

    @Named("stringToFecha")
    default public Date stringToFecha(String fechaMatriculacion) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = formatter.parse(fechaMatriculacion);
        return date;
    }
}
