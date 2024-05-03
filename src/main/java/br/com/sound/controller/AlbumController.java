package br.com.sound.controller;

import br.com.sound.dto.AlbumDto;
import br.com.sound.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrar(Long idArtista, @RequestBody AlbumDto albumDto){
        return albumService.cadastrarAlbum(idArtista, albumDto);
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<?> listarTodos(@RequestParam Integer paginas, Integer registros){
        Pageable pageable = PageRequest.of(paginas - 1, registros);
        return albumService.listarAlbuns(pageable);
    }
}
