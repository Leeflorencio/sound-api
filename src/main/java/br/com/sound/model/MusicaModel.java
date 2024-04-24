package br.com.sound.model;

import br.com.sound.dto.ArtistaDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Table(name = "musicas")
public class MusicaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 80)
    private String titulo;

    @Lob
    private String letra;

    @Column(nullable = false, length = 50)
    private String genero;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference //mapeamento e eliminação do loop infinito no método listar
    @JoinColumn(name="artista_id", updatable=true)
    private ArtistaModel artista;
}
