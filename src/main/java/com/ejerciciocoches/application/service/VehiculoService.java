package com.ejerciciocoches.application.service;

import com.ejerciciocoches.domain.ModeloDomain;
import com.ejerciciocoches.domain.mappers.ModeloDomainMapper;
import com.ejerciciocoches.domain.mappers.VehiculoDomainMapper;
import com.ejerciciocoches.domain.exceptions.DomainException;
import com.ejerciciocoches.infrastucture.repository.*;
import com.ejerciciocoches.infrastucture.repository.entity.Vehiculo;
import com.ejerciciocoches.domain.VehiculoDomain;
import com.ejerciciocoches.infrastucture.api.dto.VehiculoRequestDTO;
import com.ejerciciocoches.infrastucture.api.dto.VehiculoResponseDTO;
import com.ejerciciocoches.infrastucture.api.dto.mappers.VehiculoAPIMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final VehiculoAPIMapper vehiculoMapper;
    private final VehiculoDomainMapper vehiculoDomainMapper;
    private final ModeloDomainMapper modeloDomainMapper;

    private final ModeloService modeloService;
    private final MarcaService marcaService;

    public VehiculoService(VehiculoRepository vehiculoRepository, VehiculoAPIMapper vehiculoMapper, VehiculoDomainMapper vehiculoDomainMapper, ModeloDomainMapper modeloDomainMapper, ModeloService modeloService, MarcaService marcaService) {
        this.vehiculoMapper = vehiculoMapper;
        this.vehiculoDomainMapper = vehiculoDomainMapper;
        this.modeloDomainMapper = modeloDomainMapper;
        this.modeloService = modeloService;
        this.marcaService = marcaService;
        this.vehiculoRepository = vehiculoRepository;
    }

    /*

        Principales

     */

    //Obtiene todos los vehiculos o solo los de la marca de la idMarca que reciba
    public List<VehiculoDomain> getVehiculos(Integer idMarca) {
        List<VehiculoDomain> vehiculosList;

        if (idMarca != null && idMarca.intValue() > 0) {
            vehiculosList = findByMarcaDomain(idMarca.intValue());
        } else {
            vehiculosList = findAllDomain();
        }

        return vehiculosList;
    }

    public VehiculoDomain getVehiculo(String matricula) {
        return findByMatriculaVehiculo(matricula);
    }

    @Transactional
    public VehiculoDomain insertarVehiculo(VehiculoRequestDTO vehiculoRequestDTO) throws DomainException {
        VehiculoDomain vehiculo = convertDomainFromDTO(vehiculoRequestDTO);
        comprobarMarcaYModelo(vehiculo.getModeloDomain().getIdModelo(), vehiculo.getModeloDomain().getMarcaDomain().getIdMarca().intValue());
        //Se busca el modelo por separado
        ModeloDomain modeloVehiculoGuardar = modeloDomainMapper.modeloInfraToModeloDomain(modeloService.findByIdModelo(vehiculo.getModeloDomain().getIdModelo()));
        VehiculoDomain VehiculoGuardado = convertToVehiculoDomain(save(convertToVehiculoInfras(vehiculo)));//pasamos a vehiculo de entity
        VehiculoGuardado.setModeloDomain(modeloVehiculoGuardar);
        return VehiculoGuardado;
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


    public String deleteVehiculo(String matricula) {
        Vehiculo VehiculoDelete = vehiculoRepository.findByMatriculaVehiculo(matricula);
        vehiculoRepository.delete(VehiculoDelete);
        return matricula;
    }

    /*

        Comprobaciones

     */

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

    /*

        Converts

     */

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

    public List<VehiculoResponseDTO> convertListToDTO(List<VehiculoDomain> vehiculosDomainList) {
        return vehiculosDomainList.stream().map(this::convertToDTO).toList();
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

    /*

        Repository

     */

    public List<VehiculoDomain> findByMarcaDomain(int idMarca) {
        List<Vehiculo> listMarcas = vehiculoRepository.findByMarca(idMarca);
        return listMarcas.stream().map(this::convertToVehiculoDomain).toList();
    }

    public List<VehiculoDomain> findAllDomain() {
        List<Vehiculo> listMarcas = vehiculoRepository.findAll();
        return listMarcas.stream().map(this::convertToVehiculoDomain).toList();
    }

    public Vehiculo save(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public VehiculoDomain findByMatriculaVehiculo(String matricula) {
        return convertToVehiculoDomain(vehiculoRepository.findByMatriculaVehiculo(matricula));
    }

    public void delete(Vehiculo vehiculoDelete) {
        vehiculoRepository.delete(vehiculoDelete);
    }

}
