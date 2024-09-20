package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Aluno {
    private Long matricula;
    private String nome;
    private String telefone;
    private boolean maioridade;
    private Curso curso;
    private String sexo;

    @Override
    public String toString() {
        return "Aluno{" +
                "matricula=" + matricula +
                ", nome='" + nome + '\'' +
                ", curso=" + curso.getSigla() +  // Mostra apenas a sigla do curso
                ", maioridade=" + (maioridade ? "Sim" : "Não") +
                ", sexo='" + sexo + '\'' +
                '}';
    }
}