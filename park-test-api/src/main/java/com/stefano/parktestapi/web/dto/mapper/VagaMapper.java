package com.stefano.parktestapi.web.dto.mapper;

import com.stefano.parktestapi.entity.Vaga;
import com.stefano.parktestapi.web.dto.VagaCreateDto;
import com.stefano.parktestapi.web.dto.VagaResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {

    public static Vaga toVaga(VagaCreateDto vagaCreateDto) {
        return new ModelMapper().map(vagaCreateDto, Vaga.class);
    }

    public static VagaResponseDto toVagaResponseDto(Vaga vaga) {
        return new ModelMapper().map(vaga, VagaResponseDto.class);
    }

}
