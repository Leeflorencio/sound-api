package br.com.sound.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDto {

    private Long id;
    private String titulo;
    private LocalDate dataDeLancamento;
    private String genero;
    //private String pontuacao;
    //private Integer avalicacao;

}
