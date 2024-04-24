package br.com.sound.service;

import br.com.sound.dto.ArtistaDto;
import br.com.sound.dto.MusicaDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MusicaService {
    ResponseEntity<Object> cadastrarMusica(Long artistaId, MusicaDto musicaDto);

    ResponseEntity<?> listarMusicas(Pageable pageable);
}
