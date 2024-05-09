package br.com.sound.service.impl;

import br.com.sound.dto.PlaylistDto;
import br.com.sound.model.MusicaModel;
import br.com.sound.model.PlaylistModel;
import br.com.sound.repository.MusicaRepository;
import br.com.sound.repository.PlaylistRepository;
import br.com.sound.service.PlaylistService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class PlaylistServiceImpl implements PlaylistService {

    @Autowired
    PlaylistRepository playlistRepository;

    @Autowired
    MusicaRepository musicaRepository;

    @Override
    public ResponseEntity<Object> criarPlaylist(PlaylistDto playlistDto) {
        try {
            var playlist = new PlaylistModel();
            BeanUtils.copyProperties(playlistDto, playlist);
            playlist.setDataCriacao(LocalDate.now());
            playlist.setUltimaAtualizacao(LocalDate.now());
            playlistRepository.save(playlist);
            return ResponseEntity.status(HttpStatus.CREATED).body("Playlist criada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar a playlist: " + e);
        }
    }

    @Override
    public ResponseEntity<Object> adicionarMusicas(Long idMusica, Long idPlaylist) {

        try {
            Optional<MusicaModel> musicaOptional = musicaRepository.findById(idMusica);
            Optional<PlaylistModel> playlistOptional = playlistRepository.findById(idPlaylist);

            if (!playlistOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist não encontrada");
            } else if (!musicaOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Música não encontrada");
            } else {
                MusicaModel musica = musicaOptional.get();
                PlaylistModel playlist = playlistOptional.get();

                List<MusicaModel> musicas = playlist.getListaDeMusicas();
                musicas.add(musica);

                playlist.setListaDeMusicas(musicas);
                playlist.setUltimaAtualizacao(LocalDate.now());

                playlist.setTotalDeMusicas(musicas);
                playlistRepository.save(playlist);

                return ResponseEntity.status(HttpStatus.CREATED).body("Música adicionada com sucesso");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Música adicionada com sucesso");
        }
    }

    @Override
    public ResponseEntity<?> ListarMusicasDaPlaylist(Long idPlaylist) {
        try {
            Optional<PlaylistModel> playlistOptional = playlistRepository.findById(idPlaylist);
            if (!playlistOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist não encontrada");
            } else {
                log.info("playlist encontrada");
                PlaylistModel playlist = playlistOptional.get();
                List<MusicaModel> musicasNaPlaylist = playlist.getListaDeMusicas();

                if (musicasNaPlaylist.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A Playlist está vazia");
                } else {
                    return ResponseEntity.ok(musicasNaPlaylist);
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro : " + e);
        }
    }

    @Override
    public ResponseEntity<?> listarPlaylists(Pageable pageable) {
        try{
            Page<PlaylistModel> lista = playlistRepository.findAll(pageable);

            if (lista.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há playlist cadastrada");
            }else {
                return ResponseEntity.status(HttpStatus.OK).body(lista);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar playlists: " + e);
        }
    }


}
