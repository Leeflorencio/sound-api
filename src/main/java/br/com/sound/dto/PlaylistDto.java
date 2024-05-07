package br.com.sound.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDto {

    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataCriacao;
    private LocalDate ultimaAtualizacao;
    private Integer totalDeMusicas;
    //privacidade -> publica ou privada
    //criador -> usuario criador da playlist
}
