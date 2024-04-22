package br.com.sound.controller;

import br.com.sound.dto.ArtistaDto;
import br.com.sound.dto.MusicaDto;
import br.com.sound.model.ArtistaModel;
import br.com.sound.repository.ArtistaRepository;
import br.com.sound.service.ArtistaService;
import br.com.sound.service.MusicaService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/musicas")
public class MusicaController {

    @Autowired
    MusicaService musicaService;

    @PostMapping("/cadastro")
    public ResponseEntity<Object> cadastrar(Long artistaId, @RequestBody @Valid MusicaDto musicaDto){
           return musicaService.cadastrarMusica(artistaId, musicaDto);
    }
}
