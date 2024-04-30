package br.com.sound.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Table(name = "album")
public class AlbumModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(nullable = false)
    private LocalDate dataDeLancamento;

    @Column(nullable = false, length = 50)
    private String genero;


    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<MusicaModel> listaDeMusicas;

    @ManyToOne
    @JoinColumn(name = "artista_id")
    @JsonManagedReference //mapeamento e eliminação do loop infinito no método listar
    private ArtistaModel artistaModel;

}
