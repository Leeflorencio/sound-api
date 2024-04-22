package br.com.sound.repository;

import br.com.sound.dto.ArtistaDto;
import br.com.sound.model.ArtistaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.UUID;

@Repository
public interface ArtistaRepository extends JpaRepository<ArtistaModel, Long> {
    boolean existsByNome(String nome);

}
