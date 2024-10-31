package com.ej.msuser.service;

import com.ej.msuser.entity.ResetPasswordToken;
import com.ej.msuser.entity.Usuario;
import com.ej.msuser.exceptions.PasswordDivergentsException;
import com.ej.msuser.exceptions.ResourceNotFoundException;
import com.ej.msuser.exceptions.TokenInvalidException;
import com.ej.msuser.repository.ResetPasswordTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResetPasswordTokenService {

    private final ResetPasswordTokenRepository repository;
    private final UsuarioService service;
    private final PasswordEncoder encoder;

    @Transactional
    public void createToken(String username){
        Usuario usuario = service.findByUsername(username);
        var tk = repository.findByUsuarioIdAndExpireDateIsAfter(usuario.getId(), LocalDateTime.now());
        if(tk.isPresent()){
           throw new TokenInvalidException("Ja existe um token valido para esse usuario");
        }
        String token = UUID.randomUUID().toString();
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(usuario, token);
        repository.save(resetPasswordToken);
        //enviar email
        String texto = "http://localhost:8080/api/v1/usuarios/reset-password?token=" + token;

    }

    public Boolean isTokenValid(ResetPasswordToken token){
        return token.getExpireDate().isAfter(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public ResetPasswordToken findByToken(String token){
        return repository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("Token nao encontrado"));
    }

    @Transactional
    public void resetPassword(String token, String newPassword, String confirmNewPassword){
        var resetPasswordToken = findByToken(token);
        if(!isTokenValid(resetPasswordToken)){
            throw new TokenInvalidException("Token expirado");
        }
        if(!newPassword.equals(confirmNewPassword)){
            throw new PasswordDivergentsException("Senhas nao conferem");
        }
        var usuario = service.findById(resetPasswordToken.getUsuario().getId());
        usuario.setPassword(encoder.encode(newPassword));
        deleteById(resetPasswordToken.getId());
    }

    @Transactional
    public void deleteById(long id){
        repository.deleteById(id);
    }

}
