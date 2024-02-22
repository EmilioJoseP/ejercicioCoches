package com.ejerciciocoches.service;

import com.ejerciciocoches.exceptions.DomainException;
import com.ejerciciocoches.model.Vehiculo;
import com.ejerciciocoches.model.VehiculoRequestDTO;
import com.ejerciciocoches.model.VehiculoResponseDTO;
import com.ejerciciocoches.model.VehiculoUpdateRequestDTO;
import com.ejerciciocoches.model.mapper.VehiculoMapper;
import com.ejerciciocoches.repository.VehiculoRepository;
import lombok.AllArgsConstructor;
import org.hibernate.mapping.Array;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class VehiculoService {
    VehiculoRepository vehiculoRepository;

    VehiculoMapper vehiculoMapper;

    ModeloService modeloService;
    MarcaService marcaService;


    @Transactional
    public VehiculoResponseDTO[] updateVehiculos(VehiculoUpdateRequestDTO[] vehiculosRequestDTO) throws DomainException {
        VehiculoResponseDTO[] vehiculosResponse = new VehiculoResponseDTO[vehiculosRequestDTO.length];
        int contador = 0;
        for (VehiculoUpdateRequestDTO vehiculoRequestDTO : vehiculosRequestDTO) {
            if (comprobarMarcaYModelo(vehiculoRequestDTO.getIdModelo(), vehiculoRequestDTO.getIdMarca())) {
                Vehiculo vehiculo = vehiculoRepository.findByMatriculaVehiculo(vehiculoRequestDTO.getMatriculaVehiculo());
                vehiculo.setModelo(modeloService.findByIdModelo(vehiculoRequestDTO.getIdModelo()));
                vehiculo.getModelo().setMarca(marcaService.findByIdMarca(vehiculoRequestDTO.getIdMarca()));
                vehiculo.setPintura(vehiculoRequestDTO.getColor());
                //vehiculo.setFechaMatriculacion(vehiculoRequestDTO.getFechaMatriculacion());
                vehiculo.setCombustible(vehiculoRequestDTO.getCombustible());
                vehiculoRepository.save(vehiculo);
                vehiculosResponse[contador] = convertToDTO(vehiculo);
                contador++;
            } else {
                throw new DomainException("Vehiculo no valido");
            }
        }

        return vehiculosResponse;
    }

    @Transactional
    public VehiculoResponseDTO updateVehiculo(VehiculoUpdateRequestDTO vehiculoRequestDTO) throws DomainException {
        if (comprobarMarcaYModelo(vehiculoRequestDTO.getIdModelo(), vehiculoRequestDTO.getIdMarca())) {
            Vehiculo vehiculo = vehiculoRepository.findByMatriculaVehiculo(vehiculoRequestDTO.getMatriculaVehiculo());
            vehiculo.setModelo(modeloService.findByIdModelo(vehiculoRequestDTO.getIdModelo()));
            vehiculo.getModelo().setMarca(marcaService.findByIdMarca(vehiculoRequestDTO.getIdMarca()));
            vehiculo.setPintura(vehiculoRequestDTO.getColor());
            //vehiculo.setFechaMatriculacion(vehiculoRequestDTO.getFechaMatriculacion());
            vehiculo.setCombustible(vehiculoRequestDTO.getCombustible());
            vehiculoRepository.save(vehiculo);
            return convertToDTO(vehiculo);
        } else {
            throw new DomainException("Vehiculo no valido");
        }
    }

    public VehiculoResponseDTO insertarVehiculo(VehiculoRequestDTO vehiculoRequestDTO) throws DomainException {
        comprobarMarcaYModelo(vehiculoRequestDTO.getIdModelo(), vehiculoRequestDTO.getIdMarca());
        Vehiculo vehiculo = convertFromDTO(vehiculoRequestDTO);
        vehiculoRepository.save(vehiculo);
        return convertToDTO(vehiculo);
    }

    private boolean comprobarMarcaYModelo(int idModelo, int idMarca) throws DomainException {
        if (existeMarca(idMarca)) {
            if (existeModelo(idModelo)) {
                if (perteneceMarcaAModelo(idModelo, idMarca)) {
                    return true;
                } else {
                    throw new DomainException("Error al insertar el vehiclo. " +
                            "El modelo con id: " + idModelo + " no es de la marca con id: " + idMarca);
                }
            } else {
                throw new DomainException("Error al insertar el vehiclo. El modelo con id: " + idModelo + " no existe.");
            }
        } else {
            throw new DomainException("Error al insertar el vehiclo. La marca con id: " + idMarca + " no existe.");
        }
    }

    private boolean existeMarca(int idMarca) {
        return marcaService.existeIdMarca(idMarca);
    }

    private boolean existeModelo(int idModelo) {
        return modeloService.existeIdModelo(idModelo);
    }

    private boolean perteneceMarcaAModelo(int idModelo, int idMarca) {
        return modeloService.perteneceMarcaAModelo(idModelo, idMarca);
    }

    public List<VehiculoResponseDTO> getVehiculos(Integer idMarca) {
        List<Vehiculo> vehiculosList;
        if (idMarca != null && idMarca.intValue() > 0) {
            vehiculosList = vehiculoRepository.findByMarca(idMarca.intValue());
        } else {
            vehiculosList = vehiculoRepository.findAll();
        }

        return vehiculosList.stream().map(this::convertToDTO).toList();
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
