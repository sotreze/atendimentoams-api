package com.sauams.spring.security.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class AlterarSenhaIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void acessoAlterarSenhaSemToken() throws Exception {
        this.mockMvc
                .perform(
                        get("/alterar-senha")
                )
                .andExpect(model().attributeExists("error"))
                .andExpect(status().isOk());
    }

    @Test
    public void acessoAlterarSenhaComTokenInvalido() throws Exception {
        this.mockMvc
                .perform(
                        get("/alterar-senha?token=invalid-token")
                )
                .andExpect(model().attributeExists("error"))
                .andExpect(status().isOk());
    }

    @Test
    public void acessoAlterarSenhaComTokenExpirado() throws Exception {
        this.mockMvc
                .perform(
                        get("/alterar-senha?token=expired-token")
                )
                .andExpect(model().attributeExists("error"))
                .andExpect(status().isOk());
    }

    @Test
    public void submiteAlterarSenhaSuccess() throws Exception {
        this.mockMvc
                .perform(
                        post("/alterar-senha")
                                .with(csrf())
                                .param("senha", "senha")
                                .param("confirmeSenha", "senha")
                                .param("token", "valid-token")
                )
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/login?reset-success"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void submiteAlterarSenhaPasswordDoNotMatch() throws Exception {
        this.mockMvc
                .perform(
                        post("/reset-password")
                                .with(csrf())
                                .param("senha", "senha")
                                .param("confirmeSenha", "invalida-senha")
                                .param("token", "valid-token")
                )
                .andExpect(flash().attributeExists(BindingResult.class.getName() + ".alterarSenhaFormulario"))
                .andExpect(redirectedUrl("/alterar-senha?token=valid-token"))
                .andExpect(status().is3xxRedirection());
    }

}
