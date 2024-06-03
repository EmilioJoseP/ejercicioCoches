package com.ejerciciocoches.infrastucture.api;

import com.ejerciciocoches.domain.VehiculoDomain;
import com.ejerciciocoches.domain.exceptions.DomainException;
import com.ejerciciocoches.infrastucture.api.dto.ApiResponse;
import com.ejerciciocoches.infrastucture.api.dto.VehiculoRequestDTO;
import com.ejerciciocoches.infrastucture.api.dto.VehiculoResponseDTO;
import com.ejerciciocoches.application.service.VehiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(
        value = "/api/v1/vehiculos",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class VehiculosController {

    private final VehiculoService vehiculoService;

    public VehiculosController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @RequestMapping("/all")
    @ResponseStatus(code = HttpStatus.OK)
    public List<VehiculoResponseDTO> getAllVehiculos(@RequestParam(required = false, name = "marca") Integer idMarca) {
        List<VehiculoDomain> vehiculos = vehiculoService.getVehiculos(idMarca);
        return vehiculoService.convertListToDTO(vehiculos);
    }

    //TODO: Arreglar funciones comentadas
    @RequestMapping("/vehiculo")
    @ResponseStatus(code = HttpStatus.OK)
    public VehiculoResponseDTO getVehiculo(@RequestParam("matricula") String matricula) {
        VehiculoDomain vehiculo = vehiculoService.getVehiculo(matricula);
        return vehiculoService.convertToDTO(vehiculo);
    }

    @PostMapping(value = "insert")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ApiResponse<VehiculoResponseDTO> insertVehiculo(@RequestBody VehiculoRequestDTO vehiculoRequestDTO) throws DomainException {
        return ApiResponse.of(vehiculoService.convertToDTO(vehiculoService.insertarVehiculo(vehiculoRequestDTO)));
    }

    /*@PutMapping(value = "update")
    @ResponseStatus(code = HttpStatus.OK)
    public VehiculoResponseDTO updateVehiculo(@RequestBody VehiculoUpdateRequestDTO vehiculoRequestDTO) throws DomainException, ParseException {
        return vehiculoService.updateVehiculo(vehiculoRequestDTO);
    }

    @PutMapping(value = "updateMultiple")
    @ResponseStatus(code = HttpStatus.OK)
    public VehiculoResponseDTO[] updateVehiculos(@RequestBody VehiculoUpdateRequestDTO[] vehiculosRequestDTO) throws DomainException, ParseException {
        return vehiculoService.updateVehiculos(vehiculosRequestDTO);
    }*/

    @PostMapping(value = "delete")
    @ResponseStatus(code = HttpStatus.OK)
    public String deleteVehiculo(@RequestBody String matricula) {
        return vehiculoService.deleteVehiculo(matricula);
    }
}
