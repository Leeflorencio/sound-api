package br.com.sound.service;

import br.com.sound.dto.PlaylistDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PlaylistService {
    ResponseEntity<Object> criarPlaylist(PlaylistDto playlistDto);

    ResponseEntity<Object> adicionarMusicas(Long idMusica, Long idPlaylist);

    ResponseEntity<?> ListarMusicasDaPlaylist(Long idPlaylist);

    ResponseEntity<?> listarPlaylists(Pageable pageable);
}
