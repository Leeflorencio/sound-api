package br.com.sound.controller;

import br.com.sound.dto.PlaylistDto;
import br.com.sound.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    PlaylistService playlistService;

    @PostMapping("/criar")
    public ResponseEntity<Object> criar(@RequestBody PlaylistDto playlistDto){
        return playlistService.criarPlaylist(playlistDto);
    }

    @PostMapping("/adicionar-musicas")
    public ResponseEntity<Object> adicionarMusicas(@RequestParam Long idMusica, Long idPlaylist){
        return playlistService.adicionarMusicas(idMusica, idPlaylist);
    }

    @GetMapping("/listar-por-id")
    public ResponseEntity<?> verificarMusicasCadastradas(@RequestParam Long idPlaylist){
        return playlistService.listarMusicasDaPlaylist(idPlaylist);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listar(@RequestParam Integer paginas, Integer registros){
        Pageable pageable = PageRequest.of(paginas - 1, registros);
        return playlistService.listarPlaylists(pageable);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Object> atualizar(@RequestParam Long id, @RequestBody PlaylistDto playlistDto){
        return playlistService.atualizarPlaylist(id, playlistDto);
    }

    @DeleteMapping("/deletar-musica")
    public ResponseEntity<Object> deletarMusica(@RequestParam Long idPlaylist, Long idMusica){
        return playlistService.excluirMusica(idPlaylist, idMusica);
    }
}
