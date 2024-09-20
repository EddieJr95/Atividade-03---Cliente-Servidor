package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Curso {

    private Long Id;
    private String Nome;
    private String sigla;
    private Area area;
    public enum Area{exatas, humanas, biologicas, artes};


}