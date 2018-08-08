package com.atendimentoams.api.seguranca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.atendimentoams.api.seguranca.model.Permissao;
import com.atendimentoams.api.seguranca.model.Usuario;
import com.atendimentoams.api.seguranca.repository.UsuarioRepository;
import com.atendimentoams.api.seguranca.web.dto.UsuarioCadastroDto;

// import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario findByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public Usuario save(UsuarioCadastroDto cadastro){
        Usuario usuario = new Usuario();
        usuario.setPrimeiroNome(cadastro.getPrimeiroNome());
        usuario.setSobrenome(cadastro.getSobrenome());
        usuario.setEmail(cadastro.getEmail());
        usuario.setSenha(passwordEncoder.encode(cadastro.getSenha()));
        //usuario.setPermissoes(Arrays.asList(new Permissao("ROLE_USER")));
        return usuarioRepository.save(usuario);
    }

    @Override
    public void updateSenha(String senha, Long usuarioCodigo) {
        usuarioRepository.updateSenha(senha, usuarioCodigo);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null){
            throw new UsernameNotFoundException("Usuário ou senha inválidos.");
        }
        return new org.springframework.security.core.userdetails.User(usuario.getEmail(),
                usuario.getSenha(),
                mapRolesToAuthorities(usuario.getPermissoes()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Permissao> permissoes){
        return permissoes.stream()
                .map(role -> new SimpleGrantedAuthority(role.getDescricao()))
                .collect(Collectors.toList());
    }
}
