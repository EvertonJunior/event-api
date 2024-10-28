package com.ej.msuser.web.controllers;

import com.ej.msuser.entity.Usuario;
import com.ej.msuser.service.UsuarioService;
import com.ej.msuser.web.dtos.UsuarioCreateDto;
import com.ej.msuser.web.dtos.UsuarioResponseDto;
import com.ej.msuser.web.mappers.UsuarioMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@RequestBody @Valid UsuarioCreateDto dto){
        Usuario usuario = service.create(UsuarioMapper.toUsuario(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(usuario));
    }


}
