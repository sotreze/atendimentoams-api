package com.atendimentoams.api.seguranca.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.atendimentoams.api.seguranca.model.Usuario;
import com.atendimentoams.api.seguranca.web.dto.UsuarioCadastroDto;

public interface UsuarioService extends UserDetailsService {

    Usuario findByEmail(String email);

    Usuario save(UsuarioCadastroDto cadastro);

    void updateSenha(String senha, Long usuarioCodigo);
}
