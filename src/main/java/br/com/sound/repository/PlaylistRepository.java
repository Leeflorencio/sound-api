package br.com.sound.repository;

import br.com.sound.model.MusicaModel;
import br.com.sound.model.PlaylistModel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistModel,Long> {

}
