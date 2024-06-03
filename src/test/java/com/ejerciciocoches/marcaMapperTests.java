package com.ejerciciocoches;

import com.ejerciciocoches.domain.MarcaDomain;
import com.ejerciciocoches.domain.mappers.MarcaDomainMapper;
import com.ejerciciocoches.domain.mappers.MarcaDomainMapperImpl;
import com.ejerciciocoches.infrastucture.repository.entity.Marca;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = {MarcaDomainMapperImpl.class})
class marcaMapperTests {

    @Autowired
    MarcaDomainMapper marcaDomainMapper;

    @Test
    void testMarcarMapper() {
        MarcaDomain marcaDomain = new MarcaDomain();
        marcaDomain.setNombreMarca("Prueba");
        marcaDomain.setIdMarca(Long.getLong("1"));
        marcaDomain.setPaisOrigenMarca("España");


        Marca marca = marcaDomainMapper.marcaDomainToMarcaInfra(marcaDomain);

        Assertions.assertEquals(marca.getNombreMarca(), marcaDomain.getNombreMarca());
        Assertions.assertEquals(marca.getIdMarca(), marcaDomain.getIdMarca());
        Assertions.assertEquals(marca.getPaisOrigenMarca(), marcaDomain.getPaisOrigenMarca());

    }
}

