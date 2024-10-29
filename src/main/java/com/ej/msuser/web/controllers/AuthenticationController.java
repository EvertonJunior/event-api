package com.ej.msuser.web.controllers;

import com.ej.msuser.jwt.JwtToken;
import com.ej.msuser.jwt.JwtUtils;
import com.ej.msuser.service.UsuarioService;
import com.ej.msuser.web.dtos.UsuarioLoginDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<JwtToken> authenticate(@RequestBody @Valid UsuarioLoginDto dto){
        var authentication = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        authenticationManager.authenticate(authentication);
        var usuario = service.findByUsername(dto.getUsername());
        var token = JwtUtils.createToken(usuario);
        return ResponseEntity.ok(token);
    }
}
