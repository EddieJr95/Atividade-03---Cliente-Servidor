package org.example.dao;

import org.example.config.ConnectionFactory;
import org.example.model.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CursoDAO implements ICursoDAO {

    @Override
    public Curso create(Curso curso) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "INSERT INTO curso (nome, sigla, area) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, curso.getNome());
            preparedStatement.setString(2, curso.getSigla());
            preparedStatement.setString(3, curso.getArea().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return curso; // Retorna o curso criado
    }

    @Override
    public void update(Curso curso) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "UPDATE curso SET nome = ?, sigla = ?, area = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, curso.getNome());
            preparedStatement.setString(2, curso.getSigla());
            preparedStatement.setString(3, curso.getArea().name());
            preparedStatement.setLong(4, curso.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Curso curso) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "DELETE FROM curso WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, curso.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Curso> findAll() {
        List<Curso> cursos = new ArrayList<>();
        String query = "SELECT * FROM curso";
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Curso curso = new Curso();
                curso.setId(resultSet.getLong("id"));
                curso.setNome(resultSet.getString("nome"));
                curso.setSigla(resultSet.getString("sigla"));
                curso.setArea(Curso.Area.valueOf(resultSet.getString("area")));
                cursos.add(curso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cursos;
    }

    @Override
    public Optional<Curso> findById(Long id) {
        Curso curso = null;
        String query = "SELECT * FROM curso WHERE id = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                curso = new Curso();
                curso.setId(resultSet.getLong("id"));
                curso.setNome(resultSet.getString("nome"));
                curso.setSigla(resultSet.getString("sigla"));
                curso.setArea(Curso.Area.valueOf(resultSet.getString("area")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(curso); // Retorna um Optional com o curso ou vazio
    }

    @Override
    public List<Curso> findByArea(Curso.Area area) {
        List<Curso> cursos = new ArrayList<>();
        String query = "SELECT * FROM curso WHERE area = ?";
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, area.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Curso curso = new Curso();
                curso.setId(resultSet.getLong("id"));
                curso.setNome(resultSet.getString("nome"));
                curso.setSigla(resultSet.getString("sigla"));
                curso.setArea(area); // Define a área como enum
                cursos.add(curso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cursos;
    }

    @Override
    public Optional<Curso> findBySigla(String sigla) {
        Curso curso = null;
        String query = "SELECT * FROM curso WHERE sigla = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, sigla);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                curso = new Curso();
                curso.setId(resultSet.getLong("id"));
                curso.setNome(resultSet.getString("nome"));
                curso.setSigla(resultSet.getString("sigla"));
                curso.setArea(Curso.Area.valueOf(resultSet.getString("area")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(curso);
    }
}

