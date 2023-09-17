package com.horizon.banco.controllers;

import com.horizon.banco.entities.Conta;
import com.horizon.banco.entities.Pessoa;
import com.horizon.banco.exceptions.PessoaNotFoundException;
import com.horizon.banco.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    @Autowired
    private PessoaService pessoaService;
    @GetMapping("/")
    public String paginaInicial() {
        return "index"; // Nome da página Thymeleaf para a página inicial com botões de login e signup
    }

    @GetMapping("/login")
    public String paginaLogin() {
        return "login"; // Nome da página Thymeleaf para a página de login
    }

    @PostMapping("/login")
    public String fazerLogin(@RequestParam("cpf") String cpf, RedirectAttributes redirectAttributes) {
        try {
            // Buscar a pessoa pelo CPF
            Pessoa pessoa = pessoaService.buscarPessoaPorCpf(cpf);

            // Supondo que queremos redirecionar para a página /home
            redirectAttributes.addFlashAttribute("pessoa", pessoa);
            return "redirect:/home";

        } catch (PessoaNotFoundException e) {
            // Tratar exceção de pessoa não encontrada
            redirectAttributes.addFlashAttribute("error", "Pessoa não encontrada com o CPF: " + cpf);
            return "redirect:/login";
        }
    }
}