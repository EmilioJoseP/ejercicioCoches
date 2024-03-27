package com.ejerciciocoches.infrastucture.api.converters;

import com.ejerciciocoches.domain.enums.Combustible;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class CombustibleConverter implements Converter<String, Combustible> {

    @Override
    public Combustible convert(String source) {
        return Combustible.of(source);
    }
}
