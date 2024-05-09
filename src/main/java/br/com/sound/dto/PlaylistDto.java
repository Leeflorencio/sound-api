package br.com.sound.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDto {

    private String nome;
    private String descricao;
    //privacidade -> publica ou privada
    //criador -> usuario criador da playlist
}
