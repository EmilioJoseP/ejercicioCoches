package com.ejerciciocoches.service;

import com.ejerciciocoches.model.Marca;
import com.ejerciciocoches.model.Modelo;
import com.ejerciciocoches.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarcaService {

    @Autowired
    MarcaRepository marcaRepository;

    public Marca findByIdMarca(int idMarca) {
        return marcaRepository.findByIdMarca(idMarca);
    }

    public String findNombreByIdMarca(int idMarca) {
        return marcaRepository.findByIdMarca(idMarca).getNombreMarca();
    }

    public boolean existeIdMarca(int idMarca) {
        boolean existe = false;
        if (marcaRepository.findByIdMarca(idMarca) != null) {
            existe = true;
        }

        return existe;
    }
}
