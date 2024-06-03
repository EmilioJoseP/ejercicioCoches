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
class EjerciciocochesApplicationTests {

	//TODO: Hacer test mappers clase de test por clase


	@Autowired
	MarcaDomainMapper marcaDomainMapper;

	//o bien quitar la anotación @SpringBootTest y @Autowired y usar
	//MarcaDomainMapper marcaDomainMapper = new MarcaDomainMapperImpl();

	@Test
	void testFromModelAsNull() {
		assertNull(marcaDomainMapper.marcaDTOToMarca(null));
	}

}
