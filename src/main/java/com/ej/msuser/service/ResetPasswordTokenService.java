package com.ej.msuser.service;

import com.ej.msuser.entity.ResetPasswordToken;
import com.ej.msuser.entity.Usuario;
import com.ej.msuser.exceptions.TokenInvalidException;
import com.ej.msuser.repository.ResetPasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
       var tk = repository.findByUsuarioIdAndExpireDateIsBefore(usuario.getId(), LocalDateTime.now());
       if(tk.isPresent()){
          throw new TokenInvalidException("Ja existe um token valido para esse usuario");
       }
        String token = UUID.randomUUID().toString();
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(usuario, token);
        return repository.save(resetPasswordToken);
    }

    public Boolean isTokenValid(ResetPasswordToken token){
        return token.getExpireDate().isBefore(LocalDateTime.now());
    }


}
