package br.com.sound.service.impl;

import br.com.sound.dto.ArtistaDto;
import br.com.sound.model.ArtistaModel;
import br.com.sound.repository.ArtistaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistaServiceImplTest {

    @InjectMocks
    private ArtistaServiceImpl artistaService;

    @Mock
    private ArtistaRepository artistaRepository;

    private ArtistaDto artistaDto;
    private ArtistaModel artistaModel;

    @BeforeEach
    void setUp() {
        artistaDto = new ArtistaDto();
        artistaDto.setNome("Artista Teste");

        artistaModel = new ArtistaModel();
        BeanUtils.copyProperties(artistaDto, artistaModel);
    }

    @Test
    void deveRetornarConflitoQuandoArtistaJaCadastrado(){

        when(artistaRepository.existsByNome(artistaDto.getNome())).thenReturn(true);

        ResponseEntity<Object> response = artistaService.cadastrarArtista(artistaDto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Artista já cadastrado", response.getBody());
    }

}