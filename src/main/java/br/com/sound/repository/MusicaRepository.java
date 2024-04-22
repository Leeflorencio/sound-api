package br.com.sound.repository;

import br.com.sound.model.MusicaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MusicaRepository extends JpaRepository<MusicaModel, Long> {


}
