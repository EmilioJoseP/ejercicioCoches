package com.ejerciciocoches.service;

import com.ejerciciocoches.repository.entity.Modelo;
import com.ejerciciocoches.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeloService {

    @Autowired
    ModeloRepository modeloRepository;

    public Modelo findByIdModelo(int idModelo) {
        return modeloRepository.findByIdModelo(idModelo);
    }

    public String findNombreByIdModelo(int idModelo) {
        return modeloRepository.findByIdModelo(idModelo).getNombreModelo();
    }

    public boolean existeIdModelo(int idModelo) {
        boolean existe = false;
        if (modeloRepository.findByIdModelo(idModelo) != null) {
            existe = true;
        }

        return existe;
    }

    public boolean perteneceMarcaAModelo(int idModelo, int idMarca) {
        boolean pertenece = false;
        if (modeloRepository.findByIdModelo(idModelo).getMarca().getIdMarca() == idMarca) {
            pertenece = true;
        }
        return pertenece;
    }
}
