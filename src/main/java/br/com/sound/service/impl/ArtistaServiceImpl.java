package br.com.sound.service.impl;

import br.com.sound.dto.ArtistaDto;
import br.com.sound.model.ArtistaModel;
import br.com.sound.repository.ArtistaRepository;
import br.com.sound.service.ArtistaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
public class ArtistaServiceImpl implements ArtistaService {

    @Autowired
    ArtistaRepository artistaRepository;

    @Override
    public ResponseEntity<Object> cadastrarArtista(ArtistaDto artistaDto) {

        try {
            var artistaModel = new ArtistaModel();

            if (artistaRepository.existsByNome(artistaDto.getNome())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Artista já cadastrado");
            } else{
                BeanUtils.copyProperties(artistaDto, artistaModel);
                artistaRepository.save(artistaModel);

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


}
