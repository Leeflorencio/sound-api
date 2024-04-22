package br.com.sound.controller;

import br.com.sound.dto.ArtistaDto;
import br.com.sound.model.ArtistaModel;
import br.com.sound.service.ArtistaService;
import br.com.sound.service.impl.ArtistaServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/artistas")
public class ArtistaController {

    @Autowired
    ArtistaService artistaService;

    @PostMapping("/cadastro")
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid ArtistaDto artistaDto){
        return artistaService.cadastrarArtista(artistaDto);
    }

}
