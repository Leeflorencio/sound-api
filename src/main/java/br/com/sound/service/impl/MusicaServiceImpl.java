package br.com.sound.service.impl;

import br.com.sound.dto.MusicaDto;
import br.com.sound.model.AlbumModel;
import br.com.sound.model.ArtistaModel;
import br.com.sound.model.MusicaModel;
import br.com.sound.repository.AlbumRepository;
import br.com.sound.repository.ArtistaRepository;
import br.com.sound.repository.MusicaRepository;
import br.com.sound.service.MusicaService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class MusicaServiceImpl implements MusicaService {

    @Autowired
    MusicaRepository musicaRepository;

    @Autowired
    ArtistaRepository artistaRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Override
    public ResponseEntity<Object> cadastrarMusica(Long artistaId, Long albumId, MusicaDto musicaDto) {
        log.debug("Recebendo os dados da musica: ", musicaDto);

        try {
            var musicaModel = new MusicaModel();
            Optional<ArtistaModel> artista = artistaRepository.findById(artistaId);
            Optional<AlbumModel> album = albumRepository.findById(albumId);

            if (!artista.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista não encontrado");
            } else if (musicaRepository.existsByArtistaIdAndTitulo(artistaId, musicaDto.getTitulo())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Música já cadastrada");
            } else if (!album.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Álbum não encontrado");
            } else {
                ArtistaModel musico = artista.get();
                AlbumModel albumModel = album.get();

                musicaModel.setAlbum(albumModel);
                musicaModel.setArtista(musico);
                BeanUtils.copyProperties(musicaDto, musicaModel);
                musicaRepository.save(musicaModel);

                log.info("Musica cadastrada: ");
                return ResponseEntity.status(HttpStatus.CREATED).body("Musica cadastrada com sucesso.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro no cadastro: " + e);
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar a lista: " + e);
        }
    }

    @Override
    public ResponseEntity<Object> deletarMusicas(Long id) {
        try {
            Optional<MusicaModel> musica = musicaRepository.findById(id);
            if (!musica.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há música com o id " + id + ". Informe um id válido." );
            } else{
                MusicaModel musicaModel = musica.get();
                musicaRepository.delete(musicaModel);
                log.debug("Recebendo o id da musica: ", id);
                log.info("Musica excluida");
                return ResponseEntity.status(HttpStatus.OK).body("Musica " + musica.get().getTitulo() + " foi deletada com sucesso");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar as musicas: " + e);
        }
    }

    @Override
    public ResponseEntity<Object> buscarMusicaPorId(Long id) {
        try {
            Optional<MusicaModel> musica = musicaRepository.findById(id);

            if (!musica.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há música com o id " + id + ". Informe um id válido." );
            } else {
               return ResponseEntity.status(HttpStatus.OK).body(musica);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e);
        }
    }

    @Override
    public ResponseEntity<Object> buscarMusicaPorNome(String titulo) {
        try{
            List<MusicaModel> musicaPorNome = musicaRepository.findAllByTitulo(titulo);
            if (musicaPorNome.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontramos a música com o nome " + titulo + ". Informe um nome válido." );
            } else {
                log.info("Musicas encontradas" );
                return ResponseEntity.status(HttpStatus.OK).body(musicaPorNome);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e);
        }
    }

    @Override
    public ResponseEntity<Object> atualizarMusica(MusicaDto musicaDto, Long idMusica, Long idArtista) {
        log.info("id musica: " + idMusica);
        log.info("id artista: " + idArtista);
        try{
            Optional<MusicaModel> musicaModel = musicaRepository.findById(idMusica);
            Optional<ArtistaModel> artistaModel = artistaRepository.findById(idArtista);
            if (!musicaModel.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontramos a música com o id " + idMusica + ". Informe um id válido." );
            } else if (!artistaModel.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontramos o artista com o id " + idArtista + ". Informe um id válido." );
            } else {
                var musica = musicaModel.get();
                var cantor = artistaModel.get();

                musica.setTitulo(musicaDto.getTitulo());
                musica.setLetra(musicaDto.getLetra());
                musica.setArtista(cantor);
                if (!generoValido(musicaDto.getGenero())){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Informe um genero válido com letras entre A e Z");
                }else {
                    musica.setGenero(musicaDto.getGenero());
                    musicaRepository.save(musica);
                    return ResponseEntity.status(HttpStatus.CREATED).body("Musica atualizado com sucesso");
                }
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e);
        }
    }

    private boolean generoValido(String genero) {
        return genero.matches("^[A-Za-z\\\\/\\s]+$");
    }
}

