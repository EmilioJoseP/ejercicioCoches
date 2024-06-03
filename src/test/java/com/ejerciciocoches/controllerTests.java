package com.ejerciciocoches;

import com.ejerciciocoches.application.service.VehiculoService;
import com.ejerciciocoches.domain.ModeloDomain;
import com.ejerciciocoches.domain.VehiculoDomain;
import com.ejerciciocoches.domain.enums.Combustible;
import com.ejerciciocoches.domain.mappers.VehiculoDomainMapper;
import com.ejerciciocoches.infrastucture.api.VehiculosController;
import com.ejerciciocoches.infrastucture.api.dto.VehiculoResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({VehiculosController.class, VehiculoDomainMapper.class})
public class controllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehiculoService vehiculoService;

    public VehiculoDomainMapper vehiculoDomainMapper;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        vehiculoDomainMapper = VehiculoDomainMapper.INSTANCE;
    }

    @Test
    void testMarcarMapper() throws Exception {
        objectMapper = objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);



        VehiculoDomain vehiculo = new VehiculoDomain();
        vehiculo.setIdVehiculo(1);
        var matricula = "1111AAA";
        vehiculo.setMatriculaVehiculo(matricula);
        vehiculo.setPintura("Rojo");
        vehiculo.setModeloDomain(new ModeloDomain());
        vehiculo.setCombustible(Combustible.GASOLINA);
        vehiculo.setFechaMatriculacion(Date.valueOf(LocalDate.now()));

        VehiculoResponseDTO vehiculoDTOPrueba = new VehiculoResponseDTO();
        vehiculoDTOPrueba.setMatriculaVehiculo(matricula);
        vehiculoDTOPrueba.setColor("Rojo");
        vehiculoDTOPrueba.setCombustible(Combustible.GASOLINA);
        vehiculoDTOPrueba.setFechaMatriculacion(Date.valueOf(LocalDate.now()).toString());

        VehiculoResponseDTO vehiculoDTO = this.vehiculoDomainMapper.vehiculoToVehiculoResponseDTO(vehiculo);

        when(vehiculoService.getVehiculo(matricula)).thenReturn(vehiculo);

        String vehiculoEsperado = objectMapper.writeValueAsString(vehiculoDTO);


        this.mockMvc.perform(post("/api/v1/vehiculos/vehiculo")
                .queryParam("matricula", matricula))
                .andExpect(status().isOk()).andExpect(content().json(vehiculoEsperado));

    }

}
