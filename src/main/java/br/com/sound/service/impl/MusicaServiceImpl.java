package br.com.sound.service.impl;

import br.com.sound.dto.MusicaDto;
import br.com.sound.model.ArtistaModel;
import br.com.sound.model.MusicaModel;
import br.com.sound.repository.ArtistaRepository;
import br.com.sound.repository.MusicaRepository;
import br.com.sound.service.MusicaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public ResponseEntity<?> listarMusicas(Pageable pageable) {
        try {
            Page<MusicaModel> musicas = musicaRepository.findAll(pageable);
            if (musicas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há registros de musicas cadastradas");
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body(musicas);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar a lista " + e);
        }
    }

    @Override
    public ResponseEntity<Object> deletarMusicas(Long id) {
        try {
            Optional<ArtistaModel> artista = artistaRepository.findById(id);
            if (!artista.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há artista com o id " + id + ". Informe um id válido." );
            } else{
                ArtistaModel artistaModel = artista.get();
                artistaRepository.delete(artistaModel);
                return ResponseEntity.status(HttpStatus.OK).body("Toda as musicas do artista " + artista + "foram deletadas com sucesso.");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar as musicas " + e);
        }
    }

}

