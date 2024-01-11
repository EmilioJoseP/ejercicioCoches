package com.ejerciciocoches.controller;

import com.ejerciciocoches.model.VehiculoRequestDTO;
import com.ejerciciocoches.model.VehiculoResponseDTO;
import com.ejerciciocoches.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @Autowired
    VehiculoService vehiculoService;

    @RequestMapping("/getAllVehiculos")
    public String getAllVehiculos(@RequestParam(required = false, name = "marca") String idMarca) {
        return vehiculoService.getVehiculos(idMarca).toString();//cambiar nombre
    }

    @RequestMapping("/{matricula}/getVehiculo")
    public String getVehiculo(@PathVariable("matricula") String matricula) {
        return vehiculoService.getVehiculoDTO(matricula).toString();
    }

    @PostMapping(value = "insertVehiculo")
    public VehiculoResponseDTO insertVehiculo(@RequestBody VehiculoRequestDTO vehiculoRequestDTO) throws Exception {
        return vehiculoService.insertarVehiculoBody(vehiculoRequestDTO);
    }
}
