package org.example.dao;

import org.example.model.Curso;

import java.util.List;
import java.util.Optional;

public interface ICursoDAO {

        Curso create (Curso curso);
        void update(Curso curso);
        void delete (Curso curso);
        List<Curso> findAll();
        Optional<Curso> findById(Long id);
        List<Curso> findByArea(Curso.Area area);
        Optional<Curso> findBySigla(String sigla);


}
