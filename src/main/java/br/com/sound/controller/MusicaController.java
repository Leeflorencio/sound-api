package br.com.sound.controller;

import br.com.sound.dto.ArtistaDto;
import br.com.sound.dto.MusicaDto;
import br.com.sound.service.MusicaService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/musicas")
public class MusicaController {

    @Autowired
    MusicaService musicaService;

    @PostMapping("/cadastro")
    public ResponseEntity<Object> cadastrar(Long artistaId, Long albumId, @RequestBody @Valid MusicaDto musicaDto) {
        return musicaService.cadastrarMusica(artistaId, albumId, musicaDto);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listar(@RequestParam Integer paginas, Integer registros){
        Pageable pageable = PageRequest.of(paginas - 1, registros);
        return musicaService.listarMusicas(pageable);
    }

    @GetMapping("/buscarPorId")
    public ResponseEntity<Object> buscarPorId(@RequestParam Long id){
        return musicaService.buscarMusicaPorId(id);
    }

    @GetMapping("/buscarPorNome")
    public ResponseEntity<Object> buscarMusicaPorNome(@RequestParam String titulo){
        return musicaService.buscarMusicaPorNome(titulo);
    }

    @DeleteMapping("/deletarMusica")
    public ResponseEntity<Object> deletar(@RequestParam Long id){
        return musicaService.deletarMusicas(id);
    }


    @PutMapping("/atualizar")
    public ResponseEntity<Object> atualizar(@RequestBody MusicaDto musicaDto, Long idMusica, Long idArtista){
        return musicaService.atualizarMusica(musicaDto, idMusica, idArtista);
    }

    public String index() {
        log.trace("TRACE"); //log mais detalhado
        log.debug("DEBUG"); //detalhamento dos métodos, utilizado para desenvolvimento
        log.info("INFO"); //menos detalhado que o debug, mais utilizado em produção
        log.warn("WARN"); //log de alerta
        log.error("ERROR"); //log de erro
        return "Logging Spring Boot...";
    }

}
