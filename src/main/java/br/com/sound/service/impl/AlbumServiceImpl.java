package br.com.sound.service.impl;

import br.com.sound.dto.AlbumDto;
import br.com.sound.model.AlbumModel;
import br.com.sound.repository.AlbumRepository;
import br.com.sound.service.AlbumService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class AlbumServiceImpl implements AlbumService {


    @Autowired
    AlbumRepository albumRepository;

    @Override
    public ResponseEntity<Object> cadastrarAlbum(AlbumDto albumDto) {
        try {

            var albumModel = new AlbumModel();

            BeanUtils.copyProperties(albumDto, albumModel);
            albumRepository.save(albumModel);

            return ResponseEntity.status(HttpStatus.CONFLICT).body("Album cadastrado");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro no cadastro: " + e);
        }
    }
}
