package com.ejerciciocoches;

import com.ejerciciocoches.application.service.VehiculoService;
import com.ejerciciocoches.domain.ModeloDomain;
import com.ejerciciocoches.domain.VehiculoDomain;
import com.ejerciciocoches.domain.enums.Combustible;
import com.ejerciciocoches.domain.mappers.VehiculoDomainMapper;
import com.ejerciciocoches.infrastucture.api.VehiculosController;
import com.ejerciciocoches.infrastucture.api.dto.VehiculoRequestDTO;
import com.ejerciciocoches.infrastucture.api.dto.VehiculoResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    void testGetVehiculo() throws Exception {
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

    @Test
    void testInsertVehiculo() throws Exception {
        objectMapper = objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        VehiculoRequestDTO vehiculoInsertar = new VehiculoRequestDTO();
        var matricula = "1111AAA";
        vehiculoInsertar.setMatriculaVehiculo(matricula);
        vehiculoInsertar.setColor("Rojo");
        vehiculoInsertar.setIdModelo(1);
        vehiculoInsertar.setIdMarca(2);
        vehiculoInsertar.setCombustible(Combustible.GASOLINA);
        vehiculoInsertar.setFechaMatriculacion("23/05/2024 11:57");

        VehiculoDomain vehiculoRespuesta = new VehiculoDomain();
        vehiculoRespuesta.setMatriculaVehiculo(matricula);
        vehiculoRespuesta.setPintura("Rojo");
        vehiculoRespuesta.setModeloDomain(new ModeloDomain());
        vehiculoRespuesta.setCombustible(Combustible.GASOLINA);
        String string = "23/05/2024 11:57";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDate date = LocalDate.parse(string, formatter);
        vehiculoRespuesta.setFechaMatriculacion(Date.valueOf(date));

        when(vehiculoService.insertarVehiculo(vehiculoInsertar)).thenReturn(vehiculoRespuesta);

        VehiculoDomain vehiculoDomain = this.vehiculoDomainMapper.vehiculoRequestDTOToVehiculoDomain(vehiculoInsertar);
        String vehiculoEsperado = objectMapper.writeValueAsString(vehiculoDomain);
        String vehiculoInsertarJSON = objectMapper.writeValueAsString(vehiculoInsertar);

        this.mockMvc.perform(post("/api/v1/vehiculos/insert").contentType(MediaType.APPLICATION_JSON).content(vehiculoInsertarJSON))
                .andExpect(status().isCreated()).andExpect(content().json(vehiculoEsperado));

    }

}
