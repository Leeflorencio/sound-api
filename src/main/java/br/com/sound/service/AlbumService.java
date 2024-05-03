package br.com.sound.service;

import br.com.sound.dto.AlbumDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AlbumService {
    ResponseEntity<Object> cadastrarAlbum(Long idArtista, AlbumDto albumDto);

    ResponseEntity<?> listarAlbuns(Pageable pageable);

    ResponseEntity<Object> buscarAlbumPorId(Long id);
}
