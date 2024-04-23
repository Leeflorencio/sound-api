package br.com.sound.repository;

import br.com.sound.model.MusicaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicaRepository extends JpaRepository<MusicaModel, Long> {

    boolean existsByArtistaIdAndTitulo(Long artistaId, String titulo);


}
