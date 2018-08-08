package com.atendimentoams.api.seguranca.web.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.atendimentoams.api.seguranca.constraint.FieldMatch;

import javax.validation.constraints.AssertTrue;

@FieldMatch.List({
        @FieldMatch(primeiro = "senha", segundo = "confirmeSenha", message = "As senhas não coincidem"),
        @FieldMatch(primeiro = "email", segundo = "confirmeEmail", message = "Os emails não coincidem")
})
public class UsuarioCadastroDto {

    @NotEmpty
    private String primeiroNome;

    @NotEmpty
    private String sobrenome;

    @NotEmpty
    private String senha;

    @NotEmpty
    private String confirmeSenha;

    @Email
    @NotEmpty
    private String email;

    @Email
    @NotEmpty
    private String confirmeEmail;

    @AssertTrue
    private Boolean termo;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmeEmail() {
        return confirmeEmail;
    }

    public void setConfirmeEmail(String confirmeEmail) {
        this.confirmeEmail = confirmeEmail;
    }

    public Boolean getTermo() {
        return termo;
    }

    public void setTermo(Boolean termo) {
        this.termo = termo;
    }

}
