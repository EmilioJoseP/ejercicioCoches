package com.ejerciciocoches;

import com.ejerciciocoches.application.service.VehiculoService;
import com.ejerciciocoches.domain.ModeloDomain;
import com.ejerciciocoches.domain.VehiculoDomain;
import com.ejerciciocoches.domain.enums.Combustible;
import com.ejerciciocoches.domain.mappers.VehiculoDomainMapper;
import com.ejerciciocoches.infrastucture.api.VehiculosController;
import com.ejerciciocoches.infrastucture.api.dto.VehiculoResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehiculosController.class)
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
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();



        VehiculoDomain vehiculo = new VehiculoDomain();
        vehiculo.setIdVehiculo(1);
        vehiculo.setMatriculaVehiculo("1111AAA");
        vehiculo.setPintura("Rojo");
        vehiculo.setModeloDomain(new ModeloDomain());
        vehiculo.setCombustible(Combustible.GASOLINA);
        vehiculo.setFechaMatriculacion(Date.valueOf(LocalDate.now()));

        VehiculoResponseDTO vehiculoDTOPrueba = new VehiculoResponseDTO();
        vehiculoDTOPrueba.setMatriculaVehiculo("1111AAA");
        vehiculoDTOPrueba.setColor("Rojo");
        vehiculoDTOPrueba.setCombustible(Combustible.GASOLINA);
        vehiculoDTOPrueba.setFechaMatriculacion(Date.valueOf(LocalDate.now()).toString());

        VehiculoResponseDTO vehiculoDTO = this.vehiculoDomainMapper.vehiculoToVehiculoResponseDTO(vehiculo);

        when(vehiculoService.getVehiculo("1111AAA")).thenReturn(vehiculo);

        String vehiculoEsperado = ow.writeValueAsString(vehiculoDTO);
        //String expected = ow.writeValueAsString(InfoTestGenerator.generateValidResponse());
        //System.out.println(expected);

        this.mockMvc.perform(post("/api/v1/vehiculos/vehiculo")
                .param("matricula", "1111AAA"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(vehiculoEsperado));
    }

}
