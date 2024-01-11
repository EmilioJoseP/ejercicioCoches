package com.ejerciciocoches.model.mapper;

import com.ejerciciocoches.model.*;
import com.ejerciciocoches.service.MarcaService;
import com.ejerciciocoches.service.ModeloService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Mapper(componentModel = "spring")
public abstract class VehiculoMapper {

    @Autowired
    ModeloService modeloService;

    @Autowired
    MarcaService marcaService;

    @Mapping(target = "color", source = "pintura")
    @Mapping(target = "fechaMatriculacion", source = "fechaMatriculacion", qualifiedByName = "fechaToString")
    @Mapping(target = "modeloString", source = "modelo.nombreModelo")
    @Mapping(target = "marca", source = "modelo.marca.nombreMarca")
    @Mapping(target = "combustible", source = "combustible")
    public abstract VehiculoResponseDTO vehiculoToVehiculoResponseDTO(Vehiculo vehiculo);

    @Mapping(target = "pintura", source = "color")
    @Mapping(target = "fechaMatriculacion", source = "fechaMatriculacion", qualifiedByName = "stringToFecha")
    @Mapping(target = "modelo.idModelo", source = "idModelo")
    @Mapping(target = "modelo.marca.idMarca", source = "idMarca")
    @Mapping(target = "combustible", source = "combustible")
    public abstract Vehiculo vehiculoRequestDTOToVehiculo(VehiculoRequestDTO vehiculoRequestDTO);


    //Conversiones manuales
    @Named("fechaToString")
    public String fechaToString(Date fecha) throws ParseException {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        String fechaString = formatoFecha.format(fecha).toString();
        return fechaString;
    }

    @Named("stringToFecha")
    public Date stringToFecha(String fechaMatriculacion) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = formatter.parse(fechaMatriculacion);
        return date;
    }
}
