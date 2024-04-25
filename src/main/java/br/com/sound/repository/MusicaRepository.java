package br.com.sound.repository;

import br.com.sound.model.MusicaModel;
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

}