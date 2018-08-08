package com.atendimentoams.api.seguranca.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atendimentoams.api.seguranca.model.AlterarSenhaToken;
import com.atendimentoams.api.seguranca.model.Usuario;
import com.atendimentoams.api.seguranca.repository.AlterarSenhaTokenRepository;
import com.atendimentoams.api.seguranca.service.UsuarioService;
import com.atendimentoams.api.seguranca.web.dto.AlterarSenhaDto;

import javax.validation.Valid;

@Controller
@RequestMapping("/alterar-senha")
public class AlterarSenhaController {

    @Autowired private UsuarioService usuarioService;
    @Autowired private AlterarSenhaTokenRepository tokenRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;

    @ModelAttribute("alterarSenhaFormulario")
    public AlterarSenhaDto alterarSenha() {
        return new AlterarSenhaDto();
    }

    @GetMapping
    public String exibirPaginaAlterarSenha(@RequestParam(required = false) String token,
                                           Model model) {

        AlterarSenhaToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null){
            model.addAttribute("error", "Não foi possível encontrar um token para alterar a senha.");
        } else if (resetToken.isExpired()){
            model.addAttribute("error", "Token expirado, favor solicitar uma nova alteração de senha.");
        } else {
            model.addAttribute("token", resetToken.getToken());
        }

        return "alterar-senha";
    }

    @PostMapping
    @Transactional
    public String handlePasswordReset(@ModelAttribute("alterarSenhaFormulario") @Valid AlterarSenhaDto form,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes) {

        if (result.hasErrors()){
            redirectAttributes.addFlashAttribute(BindingResult.class.getName() + ".alterarSenhaFormulario", result);
            redirectAttributes.addFlashAttribute("alterarSenhaFormulario", form);
            return "redirect:/alterar-senha?token=" + form.getToken();
        }

        AlterarSenhaToken token = tokenRepository.findByToken(form.getToken());
        Usuario usuario = token.getUsuario();
        String updatedSenha = passwordEncoder.encode(form.getSenha());
        usuarioService.updateSenha(updatedSenha, usuario.getCodigo());
        tokenRepository.delete(token);

        return "redirect:/login?resetSuccess";
    }

}
