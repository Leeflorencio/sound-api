package br.com.sound.service;

import br.com.sound.dto.ArtistaDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ArtistaService {
    ResponseEntity<Object> cadastrarArtista(ArtistaDto artistaDto);

    Object findById(Long artistaID);

    ResponseEntity<?> listarArtistas(Pageable pageable);

    ResponseEntity<Object> buscarArtistaPorId(Long id);

    ResponseEntity<Object> deletarArtista(Long id);
}
