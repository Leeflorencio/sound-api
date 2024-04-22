package br.com.sound.service;

import br.com.sound.dto.ArtistaDto;
import br.com.sound.model.ArtistaModel;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public interface ArtistaService {
    ResponseEntity<Object> cadastrarArtista(ArtistaDto artistaDto);

    Object findById(Long artistaID);
}
