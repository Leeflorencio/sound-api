package br.com.sound.service.impl;

import br.com.sound.dto.MusicaDto;
import br.com.sound.model.ArtistaModel;
import br.com.sound.model.MusicaModel;
import br.com.sound.repository.ArtistaRepository;
import br.com.sound.repository.MusicaRepository;
import br.com.sound.service.MusicaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MusicaServiceImpl implements MusicaService {

    @Autowired
    MusicaRepository musicaRepository;

    @Autowired
    ArtistaRepository artistaRepository;

    @Override
    public ResponseEntity<Object> cadastrarMusica(Long artistaId, MusicaDto musicaDto) {
        try {
            var musicaModel = new MusicaModel();
            Optional<ArtistaModel> artista = artistaRepository.findById(artistaId);

            if (!artista.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista não encontrado");
            } else if (musicaRepository.existsByArtistaIdAndTitulo(artistaId, musicaDto.getTitulo())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Música já cadastrada");
            } else {
                ArtistaModel musico = artista.get();

                musicaModel.setArtista(musico);
                BeanUtils.copyProperties(musicaDto, musicaModel);
                musicaRepository.save(musicaModel);

                return ResponseEntity.status(HttpStatus.CREATED).body("Musica cadastrado com sucesso.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro no cadastro." + e);
        }
    }

}

