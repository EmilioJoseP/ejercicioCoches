package com.ejerciciocoches.controller;

import com.ejerciciocoches.exceptions.DomainException;
import com.ejerciciocoches.model.ApiResponse;
import com.ejerciciocoches.model.VehiculoRequestDTO;
import com.ejerciciocoches.model.VehiculoResponseDTO;
import com.ejerciciocoches.model.VehiculoUpdateRequestDTO;
import com.ejerciciocoches.service.VehiculoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api/v1/vehiculos/",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@AllArgsConstructor
public class MainController {

    VehiculoService vehiculoService;

    @RequestMapping("/all")
    @ResponseStatus(code = HttpStatus.OK)
    public List<VehiculoResponseDTO> getAllVehiculos(@RequestParam(required = false, name = "marca") Integer idMarca) {
        return vehiculoService.getVehiculos(idMarca);
    }

    @RequestMapping("/{matricula}")
    @ResponseStatus(code = HttpStatus.OK)
    public VehiculoResponseDTO getVehiculo(@PathVariable("matricula") String matricula) {
        return vehiculoService.getVehiculoDTO(matricula);
    }

    @PostMapping(value = "insert")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ApiResponse<VehiculoResponseDTO> insertVehiculo(@RequestBody VehiculoRequestDTO vehiculoRequestDTO) throws DomainException {
        return ApiResponse.of(vehiculoService.insertarVehiculo(vehiculoRequestDTO));

    }

    @PutMapping(value = "update")
    @ResponseStatus(code = HttpStatus.OK)
    public VehiculoResponseDTO updateVehiculo(@RequestBody VehiculoUpdateRequestDTO vehiculoRequestDTO) throws DomainException {
        return vehiculoService.updateVehiculo(vehiculoRequestDTO);
    }

    @PutMapping(value = "updateVehiculos")
    @ResponseStatus(code = HttpStatus.OK)
    public VehiculoResponseDTO[] updateVehiculos(@RequestBody VehiculoUpdateRequestDTO[] vehiculosRequestDTO) throws DomainException {
        return vehiculoService.updateVehiculos(vehiculosRequestDTO);
    }
}
