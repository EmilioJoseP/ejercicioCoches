package com.ejerciciocoches.infrastucture.api;

import com.ejerciciocoches.domain.exceptions.DomainException;
import com.ejerciciocoches.infrastucture.api.dto.ApiResponse;
import com.ejerciciocoches.infrastucture.api.dto.VehiculoRequestDTO;
import com.ejerciciocoches.infrastucture.api.dto.VehiculoResponseDTO;
import com.ejerciciocoches.infrastucture.api.dto.VehiculoUpdateRequestDTO;
import com.ejerciciocoches.application.service.VehiculoService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(
        value = "/api/v1/vehiculos/",
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
        List<VehiculoResponseDTO> vehiculos = vehiculoService.getVehiculos(idMarca);
        //devolver vehiculoDomain y aqui transformar a DTO.
        return vehiculos;
    }

    /*@RequestMapping("/{matricula}")
    @ResponseStatus(code = HttpStatus.OK)
    public VehiculoResponseDTO getVehiculo(@PathVariable("matricula") String matricula) {
        return vehiculoService.getVehiculoDTO(matricula);
    }*/

    @PostMapping(value = "insert")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ApiResponse<VehiculoResponseDTO> insertVehiculo(@RequestBody VehiculoRequestDTO vehiculoRequestDTO) throws DomainException {
        return ApiResponse.of(vehiculoService.convertToDTO(vehiculoService.insertarVehiculo(vehiculoRequestDTO)));//hace falta hacer otro dto?
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
