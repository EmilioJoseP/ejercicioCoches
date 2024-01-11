package com.ejerciciocoches.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MarcaRepositoryPrueba {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /*public List<Modelos> getCoches() {

        String sql = "SELECT mo.id, mo.nombre, mo.id_marca, ma.nombre as nombreMarca FROM modelos mo inner join marcas ma ON mo.id_marca = ma.id ";

        List<Modelos> listMarcas = jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new Modelos(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getInt("id_marca"),
                                rs.getString("nombreMarca")
                        )
        );

        return listMarcas;
    }*/


}
