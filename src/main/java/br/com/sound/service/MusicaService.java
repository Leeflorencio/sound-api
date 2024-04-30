package br.com.sound.service;

import br.com.sound.dto.MusicaDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MusicaService {
    ResponseEntity<Object> cadastrarMusica(Long artistaId, Long albumId, MusicaDto musicaDto);

    ResponseEntity<?> listarMusicas(Pageable pageable);

    ResponseEntity<Object> deletarMusicas(Long id);

    ResponseEntity<Object> buscarMusicaPorId(Long id);

    ResponseEntity<Object> buscarMusicaPorNome(String nome);

    ResponseEntity<Object> atualizarMusica(MusicaDto musicaDto, Long id, Long idArtista);
}
