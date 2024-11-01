package com.ej.msuser.web.controllers;

import com.ej.msuser.entity.Usuario;
import com.ej.msuser.service.ResetPasswordTokenService;
import com.ej.msuser.service.UsuarioService;
import com.ej.msuser.web.dtos.ForgotPasswordDto;
import com.ej.msuser.web.dtos.ResetPasswordDto;
import com.ej.msuser.web.dtos.UsuarioCreateDto;
import com.ej.msuser.web.dtos.UsuarioResponseDto;
import com.ej.msuser.web.mappers.UsuarioMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    private final ResetPasswordTokenService tokenService;

    @PostMapping("/cliente")
    public ResponseEntity<UsuarioResponseDto> createCliente(@RequestBody @Valid UsuarioCreateDto dto){
        Usuario usuario = service.create(UsuarioMapper.toUsuario(dto), "CLIENTE");
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(usuario));
    }

    @PostMapping("/prestador")
    public ResponseEntity<UsuarioResponseDto> createPrestador(@RequestBody @Valid UsuarioCreateDto dto){
        Usuario usuario = service.create(UsuarioMapper.toUsuario(dto), "PRESTADOR");
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(usuario));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDto> findById(@PathVariable long id){
        return ResponseEntity.ok(UsuarioMapper.toDto(service.findById(id)));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDto>> findAll(){
        return ResponseEntity.ok(service.findAll().stream().map(UsuarioMapper::toDto).toList());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotPasswordDto dto){
        tokenService.createToken(dto.getEmail());
        return ResponseEntity.ok("Email para recuperacao de senha enviado");
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestParam("token") String token, @RequestBody @Valid ResetPasswordDto dto){
        tokenService.resetPassword(token, dto.getNewPassword(), dto.getConfirmNewPassword());
        return ResponseEntity.noContent().build();
    }

}
