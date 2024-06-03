package com.ejerciciocoches;

import com.ejerciciocoches.domain.MarcaDomain;
import com.ejerciciocoches.domain.VehiculoDomain;
import com.ejerciciocoches.domain.mappers.MarcaDomainMapperImpl;
import com.ejerciciocoches.domain.mappers.VehiculoDomainMapper;
import com.ejerciciocoches.domain.mappers.VehiculoDomainMapperImpl;
import com.ejerciciocoches.infrastucture.repository.entity.Marca;
import com.ejerciciocoches.infrastucture.repository.entity.Vehiculo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {VehiculoDomainMapperImpl.class})
class vahiculoMapperTests {

    @Autowired
    VehiculoDomainMapper vehiculoDomainMapper;

    @Test
    void testVehiculoMapper() {
        VehiculoDomain vehiculoDomain = new VehiculoDomain();
        vehiculoDomain.setIdVehiculo(1);
        vehiculoDomain.setPintura("Rojo");
        vehiculoDomain.setMatriculaVehiculo("1472DDD");


        Vehiculo vehiculo = vehiculoDomainMapper.vehiculoDomainToVehiculoInfra(vehiculoDomain);

        Assertions.assertEquals(vehiculo.getIdVehiculo(), vehiculoDomain.getIdVehiculo());
        Assertions.assertEquals(vehiculo.getPintura(), vehiculoDomain.getPintura());
        Assertions.assertEquals(vehiculo.getMatriculaVehiculo(), vehiculoDomain.getMatriculaVehiculo());

    }
}

