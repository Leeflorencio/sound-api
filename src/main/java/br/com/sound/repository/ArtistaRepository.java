package br.com.sound.repository;

import br.com.sound.model.ArtistaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistaRepository extends JpaRepository<ArtistaModel, Long> {
    boolean existsByNome(String nome);

    @Query(value = "SELECT * FROM artistas WHERE nome LIKE %:nome%", nativeQuery = true)
    List<ArtistaModel> findAllByNome(@Param("nome") String nome);

}
