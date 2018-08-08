package com.atendimentoams.api.seguranca.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atendimentoams.api.seguranca.model.AlterarSenhaToken;
import com.atendimentoams.api.seguranca.model.Mail;
import com.atendimentoams.api.seguranca.model.Usuario;
import com.atendimentoams.api.seguranca.repository.AlterarSenhaTokenRepository;
import com.atendimentoams.api.seguranca.service.EmailService;
import com.atendimentoams.api.seguranca.service.UsuarioService;
import com.atendimentoams.api.seguranca.web.dto.EsqueceuSenhaDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/esqueceu-senha")
public class EsqueceuSenhaController {

    @Autowired private UsuarioService usuarioService;
    @Autowired private AlterarSenhaTokenRepository tokenRepository;
    @Autowired private EmailService emailService;

    @ModelAttribute("esqueceuSenhaFormulario")
    public EsqueceuSenhaDto forgotPasswordDto() {
        return new EsqueceuSenhaDto();
    }

    @GetMapping
    public String displayForgotPasswordPage() {
        return "esqueceu-senha";
    }

    @PostMapping
    public String processForgotPasswordForm(@ModelAttribute("esqueceuSenhaFormulario") @Valid EsqueceuSenhaDto form,
                                            BindingResult result,
                                            HttpServletRequest request) {

        if (result.hasErrors()){
            return "esqueceu-senha";
        }

        Usuario usuario = usuarioService.findByEmail(form.getEmail());
        if (usuario == null){
            result.rejectValue("email", null, "Não foi possível encontrar uma conta cadastrada com esse email.");
            return "esqueceu-senha";
        }

        AlterarSenhaToken token = new AlterarSenhaToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUsuario(usuario);
        token.setExpiryDate(30);
        tokenRepository.save(token);

        Mail mail = new Mail();
        mail.setFrom("nao-responda@amsclinica.herokuapp.com");
        mail.setTo(usuario.getEmail());
        mail.setSubject("Solicitação de alteração de senha");

        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("usuario", usuario);
        model.put("signature", "https://amsclinica.herokuapp.com");
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        model.put("resetUrl", url + "/alterar-senha?token=" + token.getToken());
        mail.setModel(model);
        emailService.sendEmail(mail);

        return "redirect:/esqueceu-senha?success";

    }

}
