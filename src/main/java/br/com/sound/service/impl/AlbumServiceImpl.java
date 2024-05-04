package br.com.sound.service.impl;

import br.com.sound.dto.AlbumDto;
import br.com.sound.model.AlbumModel;
import br.com.sound.model.ArtistaModel;
import br.com.sound.model.MusicaModel;
import br.com.sound.repository.AlbumRepository;
import br.com.sound.repository.ArtistaRepository;
import br.com.sound.repository.MusicaRepository;
import br.com.sound.service.AlbumService;
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
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    ArtistaRepository artistaRepository;

    @Autowired
    MusicaRepository musicaRepository;

    @Override
    public ResponseEntity<Object> cadastrarAlbum(Long idArtista, AlbumDto albumDto) {
        log.debug("Recebendo os dados do album: ", albumDto);
        try {
            var albumModel = new AlbumModel();
            Optional<ArtistaModel> artista = artistaRepository.findById(idArtista);

            if (!artista.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não localizamos o artista com o id: " + idArtista);
            } else if (albumRepository.existsByTitulo(albumDto.getTitulo())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("O álbum " + albumDto.getTitulo() + " já foi cadastrado.");
            } else if (!generoValido(albumDto.getGenero())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Informe um genero válido com letras entre A e Z");
            } else {
                ArtistaModel musico = artista.get();
                albumModel.setArtistaModel(musico);

                BeanUtils.copyProperties(albumDto, albumModel);
                albumRepository.save(albumModel);

                log.info("Álbum cadastrado: ");
                return ResponseEntity.status(HttpStatus.CREATED).body("Álbum cadastrado com sucesso.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro no cadastro: " + e);
        }
    }

    @Override
    public ResponseEntity<?> listarAlbuns(Pageable pageable) {
        try {
            Page<AlbumModel> listaDeAlbuns = albumRepository.findAll(pageable);

            if (listaDeAlbuns.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não tem álbuns cadastrados");
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body(listaDeAlbuns);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro na busca: " + e);
        }
    }

    @Override
    public ResponseEntity<Object> buscarAlbumPorId(Long id) {
        try {
            Optional<AlbumModel> albumModel = albumRepository.findById(id);

            if (!albumModel.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não localizamos o álbum com o id: " + id);
            } else {
                List<String> listaDeMusicas = musicaRepository.findAlbumAndMusicDetailsByAlbumId(id);
                return ResponseEntity.status(HttpStatus.CREATED).body(listaDeMusicas);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro na busca: " + e);
        }
    }

    @Override
    public ResponseEntity<Object> deletarAlbum(Long id) {
        try {
            List<MusicaModel> listaDeMusicas = musicaRepository.findAllMusicasIntoAlbum(id);
            if (!listaDeMusicas.isEmpty()) {
                musicaRepository.deleteAll(listaDeMusicas);
                log.info("deletando musicas do album ");
            }
            Optional<AlbumModel> albumModel = albumRepository.findById(id);
            if (!albumModel.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há álbum com o id " + id + ". Informe um id válido.");
            } else {
                AlbumModel album = albumModel.get();
                albumRepository.delete(album);
                return ResponseEntity.status(HttpStatus.OK).body("Album " + albumModel.get().getTitulo() + " deletado com sucesso. ");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro na exclusão: " + e);
        }
    }

    @Override
    public ResponseEntity<Object> atualizarAlbum(Long id, AlbumDto albumDto) {
        try{
            Optional<AlbumModel> albumOptional = albumRepository.findById(id);

            if (!albumOptional.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há álbum com o id " + id + ". Informe um id válido.");
            }else{
                AlbumModel album = albumOptional.get();

                album.setTitulo(albumDto.getTitulo());
                album.setDataDeLancamento(albumDto.getDataDeLancamento());
                album.setGenero(albumDto.getGenero());

                albumRepository.save(album);
                return ResponseEntity.status(HttpStatus.CREATED).body("Álbum atualizado com sucesso: " + album);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro na atualização: " + e);
        }
    }


    private boolean generoValido(String genero) {
        return genero.matches("^[A-Za-z\\\\/\\s]+$");
    }
}
