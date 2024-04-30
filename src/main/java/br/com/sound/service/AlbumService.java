package br.com.sound.service;

import br.com.sound.dto.AlbumDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AlbumService {
    ResponseEntity<Object> cadastrarAlbum(AlbumDto albumDto);
}