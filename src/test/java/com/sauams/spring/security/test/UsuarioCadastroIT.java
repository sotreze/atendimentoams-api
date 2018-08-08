package com.sauams.spring.security.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class UsuarioCadastroIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void submiteCadastroContaExistente() throws Exception {
        this.mockMvc
                .perform(
                        post("/cadastro")
                                .with(csrf())
                                .param("primeiroNome", "Objeto")
                                .param("sobrenome", "Para Cadastro")
                                .param("email", "info@cadastro.com")
                                .param("confirmeEmail", "info@cadastro.com")
                                .param("senha", "senha")
                                .param("confirmeSenha", "senha")
                                .param("termo", "on")
                )
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("usuario", "email"))
                .andExpect(status().isOk());
    }

    @Test
    public void submiteCadastroSenhaNotMatching() throws Exception {
        this.mockMvc
                .perform(
                        post("/cadastro")
                                .with(csrf())
                                .param("primeiroNome", "Objeto")
                                .param("sobrenome", "Para Cadastro")
                                .param("email", "novo@info@cadastro.com")
                                .param("confirmeEmail", "novo@info@cadastro.com")
                                .param("senha", "senha")
                                .param("confirmeSenha", "inválida")
                                .param("termo", "on")
                )
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("usuario"))
                .andExpect(status().isOk());
    }

    @Test
    public void submiteCadastroEmailNotMatching() throws Exception {
        this.mockMvc
                .perform(
                        post("/cadastro")
                                .with(csrf())
                                .param("primeiroNome", "Objeto")
                                .param("sobrenome", "Para Cadastro")
                                .param("email", "novo@info@cadastro.com")
                                .param("confirmeEmail", "diferente@cadastro.com")
                                .param("senha", "senha")
                                .param("confirmeSenha", "inválida")
                                .param("termo", "on")
                )
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("usuario"))
                .andExpect(status().isOk());
    }

    @Test
    public void submiteCadastroSuccess() throws Exception {
        this.mockMvc
                .perform(
                        post("/cadastro")
                                .with(csrf())
                                .param("primeiroNome", "Objeto")
                                .param("sobrenome", "Para Cadastro")
                                .param("email", "novo@cadastro.com")
                                .param("confirmeEmail", "novo@cadastro.com")
                                .param("senha", "senha")
                                .param("confirmeSenha", "senha")
                                .param("termo", "on")
                )
                .andExpect(redirectedUrl("/cadastro?success"))
                .andExpect(status().is3xxRedirection());
    }

}
