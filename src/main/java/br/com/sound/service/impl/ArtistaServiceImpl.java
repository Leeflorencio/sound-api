package br.com.sound.service.impl;

import br.com.sound.dto.ArtistaDto;
import br.com.sound.model.ArtistaModel;
import br.com.sound.model.MusicaModel;
import br.com.sound.repository.ArtistaRepository;
import br.com.sound.repository.MusicaRepository;
import br.com.sound.service.ArtistaService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class ArtistaServiceImpl implements ArtistaService {

    @Autowired
    ArtistaRepository artistaRepository;

    @Autowired
    MusicaRepository musicaRepository;

    @Override
    public ResponseEntity<Object> cadastrarArtista(ArtistaDto artistaDto) {
        log.info("Dados recebidos do artista: " + artistaDto);

        try {
            var artistaModel = new ArtistaModel();

            if (artistaRepository.existsByNome(artistaDto.getNome())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Artista já cadastrado");
            } else {
                BeanUtils.copyProperties(artistaDto, artistaModel);
                artistaRepository.save(artistaModel);

                log.info("Dados cadastrados: " + artistaModel);
                return ResponseEntity.status(HttpStatus.CREATED).body("Artista cadastrado com sucesso.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro no cadastro." + e);
        }

    }

    @Override
    public Object findById(Long artistaID) {
        return artistaRepository.findById(artistaID);
    }

    @Override
    public ResponseEntity<?> listarArtistas(Pageable pageable) {

        try {
            Page<ArtistaModel> artistas = artistaRepository.findAll(pageable);
            if (artistas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há registros de artistas cadastrados");
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body(artistas);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar a lista " + e);
        }
    }

    @Override
    public ResponseEntity<Object> buscarArtistaPorId(Long id) {
        try {
            Optional<ArtistaModel> artista = artistaRepository.findById(id);
            if (!artista.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há artista com o id " + id);
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body(artista);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao localizar o artista: " + e);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Object> deletarArtista(Long id) {
        //delecao em cascata
        log.info("Id artista: " + id);
        try {
            List<MusicaModel> musicasArtista = musicaRepository.findAllMusicasIntoArtistas(id);
            if (!musicasArtista.isEmpty()) {
                musicaRepository.deleteAll(musicasArtista);
            }
            Optional<ArtistaModel> artista = artistaRepository.findById(id);

            if (!artista.isPresent()) {
                log.info("Artista nao localizado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há artista com o id " + id + ". Informe um id válido.");
            } else {
                ArtistaModel artistaModel = artista.get();
                artistaRepository.delete(artistaModel);
                return ResponseEntity.status(HttpStatus.OK).body("Artista " + artista.get().getNome() + " deletado com sucesso. ");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar o artista: " + e);
        }
    }

    @Override
    public ResponseEntity<Object> buscarArtistaPorNome(String nome) {
        try {
            List<ArtistaModel> artistaPorNome = artistaRepository.findAllByNome(nome);

            if (artistaPorNome.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontramos nenhum artista com o nome " + nome + ".");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(artistaPorNome);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e);
        }
    }

    @Override
    public ResponseEntity<Object> atualizarArtista(ArtistaDto artistaDto, Long id) {
        log.info("Dados para atualizar: ", artistaDto.toString());
        try {
            Optional<ArtistaModel> artistaModel = artistaRepository.findById(id);
            if (!artistaModel.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontramos nenhum artista com o id " + id + ". Informe um id válido.");
            } else {
                var artista = artistaModel.get();
                artista.setNome(artistaDto.getNome());
                if (!generoValido(artistaDto.getGenero())) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Informe um genero válido com letras entre A e Z");
                } else {
                    artista.setGenero(artistaDto.getGenero());
                    log.info("Dados atualizado: ", artista.toString());
                    artistaRepository.save(artista);
                    return ResponseEntity.status(HttpStatus.CREATED).body("Artista atualizado com sucesso");
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e);
        }
    }

    private boolean generoValido(String genero) {
        return genero.matches("^[A-Za-z\\\\/\\s]+$");
    }
}
