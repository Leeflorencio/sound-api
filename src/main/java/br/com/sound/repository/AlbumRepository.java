package br.com.sound.repository;

import br.com.sound.model.AlbumModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumModel, Long> {


    boolean findByTitulo(String titulo);

    boolean existsByTitulo(String titulo);
}
