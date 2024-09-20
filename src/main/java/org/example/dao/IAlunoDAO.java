package org.example.dao;

import org.example.model.Aluno;
import org.example.model.Curso;

import java.util.List;
import java.util.Optional;


public interface IAlunoDAO {
    Aluno create (Aluno aluno);
    void update(Aluno aluno);
    void delete (Aluno aluno);
    Optional <Aluno> findById(Long id);
    List<Aluno> findAll();
    List<Aluno>findByCurso(Curso curso);
}
