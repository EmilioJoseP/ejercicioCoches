package com.ejerciciocoches.application.service;

import com.ejerciciocoches.domain.ModeloDomain;
import com.ejerciciocoches.domain.mappers.ModeloDomainMapper;
import com.ejerciciocoches.domain.mappers.VehiculoDomainMapper;
import com.ejerciciocoches.domain.exceptions.DomainException;
import com.ejerciciocoches.infrastucture.repository.VehiculoRepositoryImpl;
import com.ejerciciocoches.infrastucture.repository.entity.Vehiculo;
import com.ejerciciocoches.domain.VehiculoDomain;
import com.ejerciciocoches.infrastucture.api.dto.VehiculoRequestDTO;
import com.ejerciciocoches.infrastucture.api.dto.VehiculoResponseDTO;
import com.ejerciciocoches.infrastucture.api.dto.mappers.VehiculoAPIMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class VehiculoService {

    private final VehiculoRepositoryImpl vehiculoRepositoryImpl;

    private final VehiculoAPIMapper vehiculoMapper;
    private final VehiculoDomainMapper vehiculoDomainMapper;
    private final ModeloDomainMapper modeloDomainMapper;

    private final ModeloService modeloService;
    private final MarcaService marcaService;

    public VehiculoService(VehiculoRepositoryImpl vehiculoRepositoryImpl, VehiculoAPIMapper vehiculoMapper, VehiculoDomainMapper vehiculoDomainMapper, ModeloDomainMapper modeloDomainMapper, ModeloService modeloService, MarcaService marcaService) {
        this.vehiculoMapper = vehiculoMapper;
        this.vehiculoDomainMapper = vehiculoDomainMapper;
        this.modeloDomainMapper = modeloDomainMapper;
        this.modeloService = modeloService;
        this.marcaService = marcaService;
        this.vehiculoRepositoryImpl = vehiculoRepositoryImpl;
    }


    /*@Transactional
    public VehiculoResponseDTO[] updateVehiculos(VehiculoUpdateRequestDTO[] vehiculosRequestDTO) throws DomainException, ParseException {
        VehiculoResponseDTO[] vehiculosResponse = new VehiculoResponseDTO[vehiculosRequestDTO.length];
        int contador = 0;
        for (VehiculoUpdateRequestDTO vehiculoRequestDTO : vehiculosRequestDTO) {
            if (comprobarMarcaYModelo(vehiculoRequestDTO.getIdModelo(), vehiculoRequestDTO.getIdMarca())) {
                Vehiculo Vehiculo = vehiculoRepository.findByMatriculaVehiculo(vehiculoRequestDTO.getMatriculaVehiculo());
                Vehiculo.setModelo(modeloService.findByIdModelo(vehiculoRequestDTO.getIdModelo()));
                Vehiculo.getModelo().setMarca(marcaService.findByIdMarca(vehiculoRequestDTO.getIdMarca()));
                Vehiculo.setPintura(vehiculoRequestDTO.getColor());
                Vehiculo.setFechaMatriculacion(stringToFecha(vehiculoRequestDTO.getFechaMatriculacion()));
                Vehiculo.setCombustible(vehiculoRequestDTO.getCombustible());
                vehiculoRepository.save(Vehiculo);
                vehiculosResponse[contador] = convertToDTO(Vehiculo);
                contador++;
            } else {
                throw new DomainException("Vehiculo no valido");
            }
        }

        return vehiculosResponse;
    }*/

   /*@Transactional
    public VehiculoResponseDTO updateVehiculo(VehiculoUpdateRequestDTO vehiculoRequestDTO) throws DomainException, ParseException {
        if (comprobarMarcaYModelo(vehiculoRequestDTO.getIdModelo(), vehiculoRequestDTO.getIdMarca())) {
            Vehiculo Vehiculo = vehiculoRepository.findByMatriculaVehiculo(vehiculoRequestDTO.getMatriculaVehiculo());
            Vehiculo.setModelo(modeloService.findByIdModelo(vehiculoRequestDTO.getIdModelo()));
            Vehiculo.getModelo().setMarca(marcaService.findByIdMarca(vehiculoRequestDTO.getIdMarca()));
            Vehiculo.setPintura(vehiculoRequestDTO.getColor());//TODO cambiar todo al mappper
            Vehiculo.setFechaMatriculacion(stringToFecha(vehiculoRequestDTO.getFechaMatriculacion()));
            Vehiculo.setCombustible(vehiculoRequestDTO.getCombustible());
            vehiculoRepository.save(Vehiculo);
            return convertToDTO(Vehiculo);
        } else {
            throw new DomainException("Vehiculo no valido");
        }
    }*/

    @Transactional
    public VehiculoDomain insertarVehiculo(VehiculoRequestDTO vehiculoRequestDTO) throws DomainException {
        VehiculoDomain vehiculo = convertDomainFromDTO(vehiculoRequestDTO); //tiene sentido? hace falta pasarlo?
        comprobarMarcaYModelo(vehiculo.getModeloDomain().getIdModelo(), vehiculo.getModeloDomain().getMarcaDomain().getIdMarca().intValue());
        //He tenido que buscar el modelo por separado
        ModeloDomain modeloVehiculoGuardar = modeloDomainMapper.modeloInfraToModeloDomain(modeloService.findByIdModelo(vehiculo.getModeloDomain().getIdModelo()));
        VehiculoDomain VehiculoGuardado = convertToVehiculoDomain(vehiculoRepositoryImpl.save(convertToVehiculoInfras(vehiculo)));//pasamos a vehiculo de entity
        VehiculoGuardado.setModeloDomain(modeloVehiculoGuardar);
        return VehiculoGuardado;
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

    public String deleteVehiculo(String matricula) {
        Vehiculo VehiculoDelete = vehiculoRepositoryImpl.findByMatriculaVehiculo(matricula);
        vehiculoRepositoryImpl.delete(VehiculoDelete);
        return matricula;
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
        List<VehiculoDomain> vehiculosList;
        if (idMarca != null && idMarca.intValue() > 0) {
            vehiculosList = vehiculoRepositoryImpl.findByMarcaDomain(idMarca.intValue()); //cambio a vehiculoRepositoryImpl
        } else {
            vehiculosList = vehiculoRepositoryImpl.findAllDomain();//Cambio a vehiculoRepositoryImpl
        }

        return vehiculosList.stream().map(this::convertToDTO).toList();
    }

    public Vehiculo getVehiculo(String matricula) {
        return vehiculoRepositoryImpl.findByMatriculaVehiculo(matricula);
    }

    /*public VehiculoResponseDTO getVehiculoDTO(String matricula) {
        Vehiculo Vehiculo = getVehiculo(matricula);
        return convertToDTO(Vehiculo);
    }*/

    public Vehiculo convertToVehiculoInfras(VehiculoDomain vehiculo) {
        return vehiculoDomainMapper.vehiculoDomainToVehiculoInfra(vehiculo);
    }

    public VehiculoDomain convertToVehiculoDomain(Vehiculo vehiculo) {
        return vehiculoDomainMapper.vehiculoInfraToVehiculoDomain(vehiculo);
    }

    //Se usa para (Consultas)
    public VehiculoResponseDTO convertToDTO(VehiculoDomain Vehiculo) {
        return vehiculoDomainMapper.vehiculoToVehiculoResponseDTO(Vehiculo);
    }

    //Se usa para (Inserts, updates, ...).
    public Vehiculo convertFromDTO(VehiculoRequestDTO vehiculoRequestDTO) {
        return vehiculoMapper.vehiculoRequestDTOToVehiculo(vehiculoRequestDTO);
    }

    public VehiculoDomain convertDomainFromDTO(VehiculoRequestDTO vehiculoRequestDTO) {
        return vehiculoDomainMapper.vehiculoRequestDTOToVehiculoDomain(vehiculoRequestDTO);
    }

    public String fechaToString(Date fecha) throws ParseException {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        String fechaString = formatoFecha.format(fecha).toString();
        return fechaString;
    }

    public Date stringToFecha(String fechaMatriculacion) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = formatter.parse(fechaMatriculacion);
        return date;
    }
}
