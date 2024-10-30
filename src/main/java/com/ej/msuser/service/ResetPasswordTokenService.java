package com.ej.msuser.service;

import com.ej.msuser.entity.ResetPasswordToken;
import com.ej.msuser.entity.Usuario;
import com.ej.msuser.repository.ResetPasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ResetPasswordTokenService {

    @Autowired
    private ResetPasswordTokenRepository repository;

    @Autowired
    private UsuarioService service;

    @Transactional
    public ResetPasswordToken createToken(String username){
        Usuario usuario = service.findByUsername(username);
        String token = UUID.randomUUID().toString();
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(usuario, token);
        return repository.save(resetPasswordToken);
    }


}
