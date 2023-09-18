package com.horizon.banco.controllers;

import com.horizon.banco.entities.Conta;
import com.horizon.banco.entities.Pessoa;
import com.horizon.banco.entities.Transferencia;
import com.horizon.banco.entities.enums.TipoConta;
import com.horizon.banco.exceptions.PessoaNotFoundException;
import com.horizon.banco.repositories.ContaRepository;
import com.horizon.banco.repositories.PessoaRepository;
import com.horizon.banco.services.ContaService;
import com.horizon.banco.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ContaController {
    @Autowired
    private ContaService contaService;

    private Conta conta;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private ContaRepository contaRepository;

    @GetMapping("/home")
    public String exibirHome(@ModelAttribute("pessoaId") Long pessoaId, Model model) {
        // Verifica se pessoaId foi passado
        if (pessoaId == null) {
            return "redirect:/login";  // Redireciona para o login se não houver pessoaId
        }

        // Busca a pessoa pelo ID
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new PessoaNotFoundException("Pessoa não encontrada com o ID: " + pessoaId));

        // Busca as contas relacionadas a essa pessoa
        List<Conta> contas = contaRepository.findByPessoa(pessoa);

        // Adiciona os dados ao modelo para serem exibidos na página
        model.addAttribute("nomePessoa", pessoa.getNome());
        model.addAttribute("contas", contas);
        model.addAttribute("pessoa", pessoa);

        return "home";
    }
    @GetMapping("/home/transferencia")
    public String mostrarFormularioTransferencia(Model model) {
        model.addAttribute("conta", conta);
        model.addAttribute("transferencia", new Transferencia()); // Crie uma classe Transferencia para representar a transferência
        return "transferencia"; // Nome da página Thymeleaf para o formulário de transferência
    }

    @PostMapping("/realizarTransferencia")
    public String realizarTransferencia(@ModelAttribute("transferencia") Transferencia transferencia) {
        // Lógica para realizar a transferência
        // Deduzir o valor da transferência da conta e atualizar a conta de destino
        return "redirect:/home"; // Redireciona para a página inicial após a transferência
    }

    /*@GetMapping("/home/deposito")
    public String mostrarFormularioDeposito(Model model) {
        model.addAttribute("conta", conta);
        model.addAttribute("deposito", new Deposito()); // Crie uma classe Deposito para representar o depósito
        return "deposito"; // Nome da página Thymeleaf para o formulário de depósito
    }

    @PostMapping("/home/realizarDeposito")
    public String realizarDeposito(@ModelAttribute("deposito") Deposito deposito) {
        // Lógica para realizar o depósito
        // Adicionar o valor do depósito à conta
        return "redirect:/home"; // Redireciona para a página inicial após o depósito
    }

    @GetMapping("/saque")
    public String mostrarFormularioSaque(Model model) {
        model.addAttribute("conta", conta);
        model.addAttribute("saque", new Saque()); // Crie uma classe Saque para representar o saque
        return "saque"; // Nome da página Thymeleaf para o formulário de saque
    }

    @PostMapping("/realizarSaque")
    public String realizarSaque(@ModelAttribute("saque") Saque saque) {
        // Lógica para realizar o saque
        // Deduzir o valor do saque da conta
        return "redirect:/home"; // Redireciona para a página inicial após o saque
    }*/
}
