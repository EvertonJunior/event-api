package com.ej.msuser.web.mappers;

import com.ej.msuser.entity.Usuario;
import com.ej.msuser.web.dtos.UsuarioCreateDto;
import com.ej.msuser.web.dtos.UsuarioResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDto dto){
        return new ModelMapper().map(dto, Usuario.class);
    }

    public static UsuarioResponseDto toDto(Usuario usuario){
        return new ModelMapper().map(usuario, UsuarioResponseDto.class);
    }

}
