package com.atendimentoams.api.seguranca.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atendimentoams.api.seguranca.model.Usuario;
import com.atendimentoams.api.seguranca.service.UsuarioService;
import com.atendimentoams.api.seguranca.web.dto.UsuarioCadastroDto;

import javax.validation.Valid;

@Controller
@RequestMapping("/cadastro")
public class UsuarioCadastroController {

    @Autowired
    private UsuarioService usuarioService;

    @ModelAttribute("usuario")
    public UsuarioCadastroDto usuarioCadastroDto() {
        return new UsuarioCadastroDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "cadastro";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("usuario") @Valid UsuarioCadastroDto usuarioDto,
                                      BindingResult result){

        Usuario existing = usuarioService.findByEmail(usuarioDto.getEmail());
        if (existing != null){
            result.rejectValue("email", null, "Já existe um usuário cadastrado com esse email");
        }

        if (result.hasErrors()){
            return "cadastro";
        }

        usuarioService.save(usuarioDto);
        return "redirect:/cadastro?success";
    }

}
