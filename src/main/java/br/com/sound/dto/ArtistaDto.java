package br.com.sound.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistaDto {

    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String genero;


}
