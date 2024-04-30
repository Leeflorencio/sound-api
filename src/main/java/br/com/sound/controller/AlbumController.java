package br.com.sound.controller;

import br.com.sound.dto.AlbumDto;
import br.com.sound.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrar(@RequestBody AlbumDto albumDto){
        return albumService.cadastrarAlbum(albumDto);
    }
}
