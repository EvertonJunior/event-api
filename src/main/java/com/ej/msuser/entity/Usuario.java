package com.ej.msuser.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "tb_users")
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 100)
    private String username;
    @Column(nullable = false, length = 250)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role{
        ROLE_ADMIN,
        ROLE_CLIENTE,
        ROLE_PRESTADOR
    }

    @CreatedDate
    private LocalDateTime dateCreation;
    @LastModifiedDate
    private LocalDateTime dateModification;
    @CreatedBy
    private String createdFor;
    @LastModifiedBy
    private String modifiedFor;

}
