package com.atendimentoams.api.seguranca.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo;

    private String primeiroNome;
    private String sobrenome;
    private String email;
    private String senha;
    private Boolean ativo = false;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "usuario_permissao",
            joinColumns = @JoinColumn(
                    name = "codigo_usuario", referencedColumnName = "codigo"),
            inverseJoinColumns = @JoinColumn(
                    name = "codigo_permissao", referencedColumnName = "codigo"))
    private Collection<Permissao> permissoes;

    public Usuario() {
    }

    public Usuario(String primeiroNome, String sobrenome, String email, String senha, Boolean ativo) {
        this.primeiroNome = primeiroNome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.ativo = ativo;
    }

    public Usuario(String primeiroNome, String sobrenome, String email, String senha, Boolean ativo, Collection<Permissao> permissoes) {
        this.primeiroNome = primeiroNome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.ativo = ativo;
        this.permissoes = permissoes;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Collection<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(Collection<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "codigo=" + codigo +
                ", primeiroNome='" + primeiroNome + '\'' +
                ", sobreNome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + "*********" + '\'' +
                ", permissoes=" + permissoes +
                '}';
    }
}
