package com.ejerciciocoches.controller;

import com.ejerciciocoches.exceptions.SQLException;
import com.ejerciciocoches.model.VehiculoRequestDTO;
import com.ejerciciocoches.model.VehiculoResponseDTO;
import com.ejerciciocoches.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    VehiculoService vehiculoService;

    @RequestMapping("/getAllVehiculos")
    public List<VehiculoResponseDTO> getAllVehiculos(@RequestParam(required = false, name = "marca") Integer idMarca) {
        return vehiculoService.getVehiculos(idMarca);
    }

    @RequestMapping("/{matricula}/getVehiculo")
    public VehiculoResponseDTO getVehiculo(@PathVariable("matricula") String matricula) {
        return vehiculoService.getVehiculoDTO(matricula);
    }

    @PostMapping(value = "insertVehiculo")
    public VehiculoResponseDTO insertVehiculo(@RequestBody VehiculoRequestDTO vehiculoRequestDTO) {
        return vehiculoService.insertarVehiculo(vehiculoRequestDTO);
    }

    @PutMapping(value = "updateVehiculo")
    public VehiculoResponseDTO updateVehiculo(@RequestBody VehiculoRequestDTO vehiculoRequestDTO) throws Exception {
        return vehiculoService.updateVehiculo(vehiculoRequestDTO);
    }
}
