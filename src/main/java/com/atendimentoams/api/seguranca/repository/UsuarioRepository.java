package com.atendimentoams.api.seguranca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.atendimentoams.api.seguranca.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);

    @Modifying
    @Query("update Usuario u set u.senha = :senha where u.codigo = :codigo")
    void updateSenha(@Param("senha") String senha, @Param("codigo") Long codigo);
}
