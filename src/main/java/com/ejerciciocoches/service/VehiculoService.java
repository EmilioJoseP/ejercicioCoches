package com.ejerciciocoches.service;

import com.ejerciciocoches.exceptions.VehiculoException;
import com.ejerciciocoches.model.Vehiculo;
import com.ejerciciocoches.model.VehiculoRequestDTO;
import com.ejerciciocoches.model.VehiculoResponseDTO;
import com.ejerciciocoches.model.mapper.VehiculoMapper;
import com.ejerciciocoches.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public VehiculoResponseDTO updateVehiculo(VehiculoRequestDTO vehiculoRequestDTO) throws Exception {
        if (comprobarMarcaYModelo(vehiculoRequestDTO.getIdModelo(), vehiculoRequestDTO.getIdMarca())) {
            Vehiculo vehiculo = vehiculoRepository.findByMatriculaVehiculo(vehiculoRequestDTO.getMatriculaVehiculo());
            if (vehiculo != null) {
                vehiculo.setModelo(modeloService.findByIdModelo(vehiculoRequestDTO.getIdModelo()));
                vehiculo.getModelo().setMarca(marcaService.findByIdMarca(vehiculoRequestDTO.getIdMarca()));
                vehiculo.setPintura(vehiculoRequestDTO.getColor());
                vehiculo.setFechaMatriculacion(vehiculoMapper.stringToFecha(vehiculoRequestDTO.getFechaMatriculacion()));
                vehiculo.setCombustible(vehiculoRequestDTO.getCombustible());
                vehiculoRepository.save(vehiculo);
                return convertToDTO(vehiculo);
            } else {
                throw new VehiculoException("El vehiculo no existe.");
            }
        } else {
            throw new VehiculoException("Error");
        }
    }

    public VehiculoResponseDTO insertarVehiculo(VehiculoRequestDTO vehiculoRequestDTO) throws VehiculoException {
        comprobarMarcaYModelo(vehiculoRequestDTO.getIdModelo(), vehiculoRequestDTO.getIdMarca());
        Vehiculo vehiculo = convertFromDTO(vehiculoRequestDTO);
        vehiculoRepository.save(vehiculo);
        return convertToDTO(vehiculo);
    }

    private boolean comprobarMarcaYModelo(int idModelo, int idMarca) throws VehiculoException {
        if (existeMarca(idMarca)) {
            if (existeModelo(idModelo)) {
                if (perteneceMarcaAModelo(idModelo, idMarca)) {
                    return true;
                } else {
                    throw new VehiculoException("Error al insertar el vehiclo. " +
                            "El modelo con id: " + idModelo + " no es de la marca con id: " + idMarca);
                }
            } else {
                throw new VehiculoException("Error al insertar el vehiclo. El modelo con id: " + idModelo + " no existe.");
            }
        } else {
            throw new VehiculoException("Error al insertar el vehiclo. La marca con id: " + idMarca + " no existe.");
        }
    }

    private boolean existeMarca(int idMarca) {
        if (marcaService.existeIdMarca(idMarca)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean existeModelo(int idModelo) {
        if (modeloService.existeIdModelo(idModelo)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean perteneceMarcaAModelo(int idModelo, int idMarca) {
        if (modeloService.perteneceMarcaAModelo(idModelo, idMarca)) {
            return true;
        } else {
            return false;
        }
    }

    public List<VehiculoResponseDTO> getVehiculos(Integer idMarca) {
        List<Vehiculo> vehiculosList = new ArrayList<>();
        if (idMarca != null && idMarca.intValue() > 0) {
            vehiculosList = vehiculoRepository.findByMarca(idMarca.intValue());
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
