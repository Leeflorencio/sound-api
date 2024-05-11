package br.com.sound.repository;

import br.com.sound.model.MusicaModel;
import br.com.sound.model.PlaylistModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicaRepository extends JpaRepository<MusicaModel, Long> {

    boolean existsByArtistaIdAndTitulo(Long artistaId, String titulo);

    @Query(value = "SELECT * FROM musicas WHERE artista_id = :id", nativeQuery = true)
    List<MusicaModel> findAllMusicasIntoArtistas(@Param("id") Long id);

    @Query(value = "SELECT * FROM musicas WHERE titulo LIKE %:titulo%", nativeQuery = true)
    List<MusicaModel> findAllByTitulo(@Param("titulo") String titulo);

    @Query(value = "SELECT * FROM musicas WHERE album_id = :id", nativeQuery = true)
    List<MusicaModel> findAllMusicasIntoAlbum(@Param("id") Long id);

    @Query(value = "SELECT CONCAT('Album: ', a.titulo) AS titulo_album, " +
            "CONCAT(' Data lançamento: ', a.data_de_lancamento) AS data_de_lancamento_album, " +
            "CONCAT(' Genero: ', a.genero) AS genero_album, " +
            "CONCAT(' Musica: ', m.titulo) AS titulo_musica, " +
            "CONCAT(' id artista: ', m.artista_id) AS id_artista " +
            "FROM album AS a " +
            "JOIN musicas AS m ON a.id = m.album_id " +
            "WHERE a.id = :idAlbum", nativeQuery = true)
    List<String> findAlbumAndMusicDetailsByAlbumId(@Param("idAlbum") Long idAlbum);

    @Query("SELECT COUNT(m) > 0 FROM MusicaModel m JOIN m.playlists playlist WHERE m = :musica AND playlist = :playlist")
    boolean existsMusicaIntoPlaylist(MusicaModel musica, PlaylistModel playlist);

}
