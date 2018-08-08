package com.atendimentoams.api.seguranca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atendimentoams.api.seguranca.model.AlterarSenhaToken;

@Repository
public interface AlterarSenhaTokenRepository extends JpaRepository<AlterarSenhaToken, Long> {

    AlterarSenhaToken findByToken(String token);

}
