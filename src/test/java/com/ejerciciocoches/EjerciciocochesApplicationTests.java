package com.ejerciciocoches;

import com.ejerciciocoches.domain.MarcaDomain;
import com.ejerciciocoches.domain.mappers.MarcaDomainMapper;
import com.ejerciciocoches.infrastucture.api.dto.MarcaDTO;
import com.ejerciciocoches.infrastucture.repository.entity.Marca;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.mockito.internal.matchers.Equals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EjerciciocochesApplicationTests {

	@Autowired
	MarcaDomainMapper marcaDomainMapper;

	@Test
	void contextLoads() {
	}

	@Test
	void testMarcarMapper() {
		MarcaDomain marcaDomain = new MarcaDomain();
		marcaDomain.setNombreMarca("Prueba");
		marcaDomain.setIdMarca(Long.getLong("1"));
		marcaDomain.setPaisOrigenMarca("España");


		Marca marca = marcaDomainMapper.marcaDomainToMarcaInfra(marcaDomain);


		Assertions.assertEquals(marca, marcaDomain);
	}
}
