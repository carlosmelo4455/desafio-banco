package com.horizon.banco.services;

import com.horizon.banco.entities.Conta;
import com.horizon.banco.entities.Pessoa;
import com.horizon.banco.entities.enums.TipoConta;
import com.horizon.banco.exceptions.PessoaNotFoundException;
import com.horizon.banco.repositories.ContaRepository;
import com.horizon.banco.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {
    @Autowired
    private ContaRepository contaRepository;
    private PessoaRepository pessoaRepository;

    public Conta criarConta(Pessoa pessoa, String numero, String digito, double saldo, TipoConta tipoConta) {

        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(pessoa.getId());
        Pessoa pessoaExistente = pessoaOptional.orElseThrow(() -> new PessoaNotFoundException("Pessoa não encontrada"));

        Optional<Conta> contaExistenteDoTipo = contaRepository.findByPessoaAndTipoConta(pessoaExistente, String.valueOf(tipoConta));
        if (contaExistenteDoTipo.isPresent()) {
            throw new RuntimeException("A pessoa já possui uma conta do tipo " + tipoConta);
        }

        Conta novaConta = new Conta(null, pessoaExistente, numero, digito, saldo, tipoConta);

        return contaRepository.save(novaConta);
    }

    public double consultarSaldo(String numero, String digito) {
        Optional<Conta> contaOptional = contaRepository.findByNumeroAndDigito(numero, digito);

        if (contaOptional.isPresent()) {
            Conta conta = contaOptional.get();
            return conta.getSaldo();
        } else {
            throw new RuntimeException("Conta não encontrada para o número e dígito especificados");
        }
    }

    public void realizarDeposito(Conta conta, double valor) {

        double saldoAtual = conta.getSaldo();
        conta.setSaldo(saldoAtual + valor);
        contaRepository.save(conta);

    }

    public void realizarSaque(Conta conta, double valor) {

        double saldoAtual = conta.getSaldo();
        if (saldoAtual < valor) {
            throw new RuntimeException("Saldo insuficiente para realizar o saque");
        }
        conta.setSaldo(saldoAtual - valor);
        contaRepository.save(conta);
    }
}
