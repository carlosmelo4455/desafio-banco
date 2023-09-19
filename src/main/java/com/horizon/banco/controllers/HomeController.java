package com.horizon.banco.controllers;

import com.horizon.banco.entities.Conta;
import com.horizon.banco.entities.Pessoa;
import com.horizon.banco.entities.enums.TipoConta;
import com.horizon.banco.exceptions.PessoaNotFoundException;
import com.horizon.banco.repositories.ContaRepository;
import com.horizon.banco.repositories.PessoaRepository;
import com.horizon.banco.services.ContaService;
import com.horizon.banco.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private ContaService contaService;

    @GetMapping("/")
    public String paginaInicial() {
        return "index";
    }
    @GetMapping("/user")
    public String showAllUsers(Model model) {
        // lista do banco de dados "Pessoa"
        List<Pessoa> usuarios = pessoaService.buscarTodosUsuarios();

        // Adiciona a lista de usuários ao frontEnd
        model.addAttribute("usuarios", usuarios);

        return "user";
    }

    @GetMapping("/login")
    public String paginaLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String fazerLogin(@RequestParam("cpf") String cpf, RedirectAttributes redirectAttributes, Model model) {
        // autentificação do usuario por CPF
        try {
            Pessoa pessoa = pessoaService.buscarPessoaPorCpf(cpf);
            redirectAttributes.addAttribute("pessoaId", pessoa.getId());
            return "redirect:/home";

        } catch (PessoaNotFoundException e) {
            redirectAttributes.addAttribute("error", "Pessoa não encontrada com o CPF: " + cpf);
            return "redirect:/login";
        }
    }

    @GetMapping("/sign")
    public String paginaSignIn(Model model) {
        // inicializa o objeto para cadastro
        model.addAttribute("pessoa", new Pessoa());
        return "sign";
    }

    @PostMapping("/sign")
    public String cadastro(@ModelAttribute Pessoa pessoa,
                           @RequestParam("tipoConta") TipoConta tipoConta,
                           RedirectAttributes redirectAttributes) {
        pessoaService.salvarPessoa(pessoa);
        // endereça conta para o usuario novo
        Conta conta = new Conta();
        conta.setPessoa(pessoa);
        conta.gerarDigito();
        conta.gerarNumeroConta();
        conta.setSaldo(0);
        conta.setTipoConta(tipoConta);

        contaService.salvarConta(conta);
        // envia apenas o id do novo cadastro para o controller do /home
        redirectAttributes.addAttribute("pessoaId", pessoa.getId());
        return "redirect:/home";
    }
}