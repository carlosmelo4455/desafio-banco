package com.horizon.banco.services;

import com.horizon.banco.entities.Pessoa;
import com.horizon.banco.exceptions.PessoaNotFoundException;
import com.horizon.banco.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa buscarPessoaPorId(Long id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        if (((Optional<?>) pessoaOptional).isPresent()) {
            return pessoaOptional.get();
        } else {
            throw new PessoaNotFoundException("Pessoa não encontrada com o ID: " + id);
        }
    }

    public Pessoa buscarPessoaPorCpf(String cpf) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findByCpf(cpf);
        if (pessoaOptional.isPresent()) {
            return pessoaOptional.get();
        } else {
            throw new PessoaNotFoundException("Pessoa não encontrada com o CPF: " + cpf);
        }
    }
}
