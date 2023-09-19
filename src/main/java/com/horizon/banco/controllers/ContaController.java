package com.horizon.banco.controllers;

import com.horizon.banco.entities.Conta;
import com.horizon.banco.entities.Pessoa;
import com.horizon.banco.entities.enums.TipoConta;
import com.horizon.banco.exceptions.PessoaNotFoundException;
import com.horizon.banco.repositories.ContaRepository;
import com.horizon.banco.repositories.PessoaRepository;
import com.horizon.banco.services.ContaService;
import com.horizon.banco.services.TransferenciaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.List;

@Controller
public class ContaController {
    private static final Logger logger = LoggerFactory.getLogger(ContaController.class);
    @Autowired
    private ContaService contaService;
    private Pessoa pessoaLogada;
    private Conta contaLogada;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private TransferenciaService transferenciaService;

    @GetMapping("/home")
    public String exibirHome(@RequestParam("pessoaId") Long pessoaId, Model model) {
        // tratamento de erros, caso o PessoaId não seja passado
        if (pessoaId == null) {
            return "redirect:/login";
        }

        // Busca a pessoa logada no banco pelo seu ID
        this.pessoaLogada = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new PessoaNotFoundException("Pessoa não encontrada com o ID: " + pessoaId));

        // Busca as contas relacionadas a essa pessoa
        List<Conta> contas = contaRepository.findByPessoa(pessoaLogada);
        if (!contas.isEmpty()) {
            this.contaLogada = contas.get(0); // Assume a primeira conta do usuário como conta atual
        }
        List<Conta> todasContas = contaRepository.findAll();
        // Adiciona os dados ao frontEnd
        model.addAttribute("nomePessoa", pessoaLogada.getNome());
        model.addAttribute("contas", contas);
        model.addAttribute("pessoa", pessoaLogada);
        model.addAttribute("todasContas", todasContas);
        return "home";
    }

    @PostMapping("/adicionarConta")
    @ResponseBody
    public ResponseEntity<String> adicionarConta(RedirectAttributes redirectAttributes) {
        // Verificar se o usuário já possui duas contas
        List<Conta> contas = contaRepository.findByPessoa(pessoaLogada);
        if (contas.size() >= 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O usuário já possui duas contas.");
        }
        // instancia uma nova conta
        Conta novaConta = new Conta();
        novaConta.setNumero(contaLogada.getNumero());
        novaConta.setDigito(contaLogada.getDigito());
        novaConta.setPessoa(contaLogada.getPessoa());

        // Define o tipo da nova conta (alternando entre C e P)
        if (this.contaLogada.getTipoConta() == TipoConta.C) {
            novaConta.setTipoConta(TipoConta.P);
        } else {
            novaConta.setTipoConta(TipoConta.C);

        }
        contaRepository.save(novaConta);
        // redireciona sempre o pessoaId de volta pro /home
        redirectAttributes.addAttribute("pessoaId", pessoaLogada.getId());
        return ResponseEntity.ok("Nova conta adicionada com sucesso.");
    }

    @PostMapping("/realizarDeposito")
    @ResponseBody
    public ResponseEntity<String> realizarDeposito(@RequestBody Map<String, String> requestBody) {

        TipoConta tipoConta = TipoConta.valueOf(requestBody.get("tipoConta"));
        double valorDeposito = Double.parseDouble(requestBody.get("valor"));

        // Obtém a conta a partir do tipo
        List<Conta> contas = contaRepository.findByPessoaAndTipoConta(pessoaLogada, tipoConta);
        Conta contaDeposito = contas.stream()
                .filter(conta -> conta.getTipoConta() == tipoConta)
                .findFirst()
                .orElse(null);

        if (contaDeposito == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de conta inválido.");
        }

        // Realiza o depósito
        contaService.realizarDeposito(contaDeposito, valorDeposito);

        return ResponseEntity.ok("Depósito realizado com sucesso.");
    }

    @PostMapping("/realizarSaque")
    @ResponseBody
    public ResponseEntity<String> realizarSaque(@RequestBody Map<String, String> requestBody) {

        TipoConta tipoConta = TipoConta.valueOf(requestBody.get("tipoConta"));
        double valorSaque = Double.parseDouble(requestBody.get("valor"));
        logger.info(String.valueOf(TipoConta.valueOf(requestBody.get("tipoConta"))));

        //busca a conta no banco de dados
        List<Conta> contas = contaRepository.findByPessoaAndTipoConta(pessoaLogada, tipoConta);
        Conta contaSaque = contas.stream()
                .filter(conta -> conta.getTipoConta() == tipoConta)
                .findFirst()
                .orElse(null);
        logger.info(String.valueOf(contaSaque.getTipoConta()));

        // Realiza o saque
        contaService.realizarSaque(contaSaque, valorSaque);

        return ResponseEntity.ok("Saque realizado com sucesso.");
    }

    @PostMapping("/realizarTransferencia")
    @ResponseBody
    public ResponseEntity<String> realizarTransferencia(@RequestBody Map<String, String> requestBody) {

        TipoConta tipoConta = TipoConta.valueOf(requestBody.get("tipoConta"));
        long contaDestinoId = Long.parseLong(requestBody.get("contaDestinoId"));
        double valorTransferencia = Double.parseDouble(requestBody.get("valorTransferencia"));
        String dataTransferencia = requestBody.get("dataTransferencia");

        //busca a conta desejada para transferir no banco de dados
        List<Conta> contas = contaRepository.findByPessoaAndTipoConta(pessoaLogada, tipoConta);
        Conta contaTransferencia = contas.stream()
                .filter(conta -> conta.getTipoConta() == tipoConta)
                .findFirst()
                .orElse(null);

        // Chame o transferenciaService para realizar a transferencia
        transferenciaService.realizarTransferencia(contaTransferencia, contaDestinoId, valorTransferencia, dataTransferencia);
        return ResponseEntity.ok("Transferência realizada com sucesso.");
    }
}

