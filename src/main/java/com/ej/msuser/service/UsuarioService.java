package com.ej.msuser.service;

import com.ej.msuser.entity.Usuario;
import com.ej.msuser.exceptions.ResourceNotFoundException;
import com.ej.msuser.exceptions.UsernameUniqueViolationException;
import com.ej.msuser.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    private final PasswordEncoder encoder;

    @Transactional
    public Usuario create(Usuario usuario, String role){
        try{
            usuario.setPassword(encoder.encode(usuario.getPassword()));
            usuario.setRole(Usuario.Role.valueOf("ROLE_"+role.toUpperCase()));
            return repository.save(usuario);
        } catch (DataIntegrityViolationException e){
            throw new UsernameUniqueViolationException("Usuario ja cadastrado no sistema");
        }
    }

    @Transactional(readOnly = true)
    public Usuario findById(long id){
        return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id " + id + " nao encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAll(){
        return repository.findAll();
    }

    @Transactional
    public void deleteById(long id){
        findById(id);
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Usuario findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado"));
    }

}
