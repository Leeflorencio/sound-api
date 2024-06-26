package br.com.sound.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference //mapeamento e eliminação do loop infinito no método listar
    @JoinColumn(name = "artista_id", updatable = true)
    private ArtistaModel artista;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "album_id")
    @JsonBackReference
    private AlbumModel album;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonManagedReference
    @ManyToMany(mappedBy = "listaDeMusicas")
    private List<PlaylistModel> playlists;

}
