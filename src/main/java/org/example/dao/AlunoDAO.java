package org.example.dao;

import org.example.config.ConnectionFactory;
import org.example.model.Aluno;
import org.example.model.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlunoDAO implements IAlunoDAO {

    @Override
    public Aluno create(Aluno aluno) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "INSERT INTO aluno (nome, telefone, maioridade, curso_id, sexo) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, aluno.getNome());
            preparedStatement.setString(2, aluno.getTelefone());
            preparedStatement.setBoolean(3, aluno.isMaioridade());
            preparedStatement.setLong(4, aluno.getCurso().getId());
            preparedStatement.setString(5, aluno.getSexo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return aluno;
    }

    @Override
    public void update(Aluno aluno) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "UPDATE aluno SET nome = ?, sexo = ?, curso_id = ?, maioridade = ?, telefone = ? WHERE matricula = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, aluno.getNome());
            preparedStatement.setString(2, aluno.getSexo());
            preparedStatement.setLong(3, aluno.getCurso().getId());
            preparedStatement.setBoolean(4, aluno.isMaioridade());
            preparedStatement.setString(5, aluno.getTelefone());
            preparedStatement.setLong(6, aluno.getMatricula());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Aluno aluno) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "DELETE FROM aluno WHERE matricula = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, aluno.getMatricula());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Aluno> findById(Long id) {
        Aluno aluno = null;
        String query = "SELECT a.*, c.sigla FROM aluno a JOIN curso c ON a.curso_id = c.id WHERE a.matricula = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                aluno = new Aluno();
                aluno.setMatricula(resultSet.getLong("matricula"));
                aluno.setNome(resultSet.getString("nome"));
                aluno.setTelefone(resultSet.getString("telefone"));
                aluno.setMaioridade(resultSet.getBoolean("maioridade"));
                aluno.setSexo(resultSet.getString("sexo"));
                Curso curso = new Curso();
                curso.setSigla(resultSet.getString("sigla"));
                aluno.setCurso(curso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(aluno);
    }

    @Override
    public List<Aluno> findAll() {
        List<Aluno> alunos = new ArrayList<>();
        String query = "SELECT a.*, c.sigla FROM aluno a JOIN curso c ON a.curso_id = c.id";
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Aluno aluno = new Aluno();
                aluno.setMatricula(resultSet.getLong("matricula"));
                aluno.setNome(resultSet.getString("nome"));
                aluno.setTelefone(resultSet.getString("telefone"));
                aluno.setMaioridade(resultSet.getBoolean("maioridade"));
                aluno.setSexo(resultSet.getString("sexo"));
                Curso curso = new Curso();
                curso.setSigla(resultSet.getString("sigla"));
                aluno.setCurso(curso);
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alunos;
    }

    @Override
    public List<Aluno> findByCurso(Curso curso) {
        List<Aluno> alunos = new ArrayList<>();
        String query = "SELECT a.* FROM aluno a WHERE a.curso_id = ?";
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, curso.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Aluno aluno = new Aluno();
                aluno.setMatricula(resultSet.getLong("matricula"));
                aluno.setNome(resultSet.getString("nome"));
                aluno.setTelefone(resultSet.getString("telefone"));
                aluno.setMaioridade(resultSet.getBoolean("maioridade"));
                aluno.setSexo(resultSet.getString("sexo"));
                aluno.setCurso(curso);
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alunos;
    }
}
