package br.com.sound.repository;

import br.com.sound.model.MusicaModel;
import br.com.sound.model.PlaylistModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistModel,Long> {

}
