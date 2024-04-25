package br.com.sound.repository;

import br.com.sound.dto.ArtistaDto;
import br.com.sound.model.ArtistaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@Repository
public interface ArtistaRepository extends JpaRepository<ArtistaModel, Long> {
    boolean existsByNome(String nome);

    @Query(value = "SELECT * FROM artistas WHERE nome LIKE %:nome%", nativeQuery = true)
    List<ArtistaModel> findAllByNome(@Param("nome") String nome);
}
