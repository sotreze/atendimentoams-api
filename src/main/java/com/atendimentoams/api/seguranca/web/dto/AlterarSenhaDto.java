package com.atendimentoams.api.seguranca.web.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.atendimentoams.api.seguranca.constraint.FieldMatch;

@FieldMatch(primeiro = "senha", segundo = "confirmeSenha", message = "As senhas devem ser iguais")
public class AlterarSenhaDto {

    @NotEmpty
    private String senha;

    @NotEmpty
    private String confirmeSenha;

    @NotEmpty
    private String token;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmeSenha() {
        return confirmeSenha;
    }

    public void setConfirmeSenha(String confirmeSenha) {
        this.confirmeSenha = confirmeSenha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
