package com.ejerciciocoches.controller.converters;

import com.ejerciciocoches.model.Combustible;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class CombustibleConverter implements Converter<String, Combustible> {

    @Override
    public Combustible convert(String source) {
        return Combustible.of(source);
    }
}
