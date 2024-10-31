package com.ej.msuser.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class ResetPasswordToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String token;
    private LocalDateTime expireDate = LocalDateTime.now().plusMinutes(30);
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public ResetPasswordToken(Usuario usuario, String token){
        this.usuario = usuario;
        this.token = token;
    }

}
