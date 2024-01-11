package com.ejerciciocoches.service;

import com.ejerciciocoches.model.Vehiculo;
import com.ejerciciocoches.model.VehiculoRequestDTO;
import com.ejerciciocoches.model.VehiculoResponseDTO;
import com.ejerciciocoches.model.mapper.VehiculoMapper;
import com.ejerciciocoches.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiculoService {
    @Autowired
    VehiculoRepository vehiculoRepository;

    @Autowired
    VehiculoMapper vehiculoMapper;

    @Autowired
    ModeloService modeloService;

    @Autowired
    MarcaService marcaService;

    public VehiculoResponseDTO insertarVehiculoBody(VehiculoRequestDTO vehiculoRequestDTO) throws Exception {
        if (modeloService.existeIdModelo(vehiculoRequestDTO.getIdModelo()) &&
            marcaService.existeIdMarca(vehiculoRequestDTO.getIdMarca()) &&
            modeloService.perteneceMarcaAModelo(vehiculoRequestDTO.getIdModelo(), vehiculoRequestDTO.getIdMarca())) {
                Vehiculo vehiculo = convertFromDTO(vehiculoRequestDTO);
                vehiculoRepository.save(vehiculo);
                VehiculoResponseDTO response = convertToDTO(vehiculo);
                return response;//TODO devolver a response antes de devolver
        } else {
             throw new Exception("No se ha podido insertar");//lanzo excepcion;
        }
    }

    public List<VehiculoResponseDTO> getVehiculos(String idMarca) {
        List<Vehiculo> vehiculosList = new ArrayList<>();
        if (idMarca != null && idMarca.length() > 0) {
            vehiculosList = vehiculoRepository.findByMarca(Integer.parseInt(idMarca));
        } else {
            vehiculosList = vehiculoRepository.findAll();
        }

        return vehiculosList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Vehiculo getVehiculo(String matricula) {
        return vehiculoRepository.findByMatriculaVehiculo(matricula);
    }

    public VehiculoResponseDTO getVehiculoDTO(String matricula) {
        Vehiculo vehiculo = getVehiculo(matricula);
        return convertToDTO(vehiculo);
    }

    //Se usa para (Consultas)
    public VehiculoResponseDTO convertToDTO(Vehiculo vehiculo) {
        return vehiculoMapper.vehiculoToVehiculoResponseDTO(vehiculo);
    }

    //Se usa para (Inserts, updates, ...).
    public Vehiculo convertFromDTO(VehiculoRequestDTO vehiculoRequestDTO) {
        return vehiculoMapper.vehiculoRequestDTOToVehiculo(vehiculoRequestDTO);
    }
}
