package br.com.sound.controller;

import br.com.sound.dto.ArtistaDto;
import br.com.sound.service.ArtistaService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/listarTodos")
    public ResponseEntity<?> listar(@RequestParam Integer paginas, Integer registros){
        Pageable pageable = PageRequest.of(paginas - 1, registros);
        return artistaService.listarArtistas(pageable);
    }

    @GetMapping("/buscarPorId")
    public ResponseEntity<Object> buscarPorId(@RequestParam Long id){
        return artistaService.buscarArtistaPorId(id);
    }

    @GetMapping("/buscarPorNome")
    public ResponseEntity<Object> buscarArtistaPorNome(@RequestParam String nome){
        return artistaService.buscarArtistaPorNome(nome);
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Object> deletar(@RequestParam Long id){
        return artistaService.deletarArtista(id);
    }


    @PutMapping("/atualizar")
    public ResponseEntity<Object> atualizar(@RequestBody ArtistaDto artistaDto, Long id){
        return artistaService.atualizarArtista(artistaDto, id);
    }
}
