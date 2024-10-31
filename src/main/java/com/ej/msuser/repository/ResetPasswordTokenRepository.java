package com.ej.msuser.repository;

import com.ej.msuser.entity.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {


    Optional<ResetPasswordToken> findByUsuarioIdAndExpireDateIsAfter(long id, LocalDateTime localDateTime);

    Optional<ResetPasswordToken> findByToken(String token);

}
