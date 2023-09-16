package com.horizon.banco.services;

import com.horizon.banco.entities.Conta;
import com.horizon.banco.entities.Pessoa;
import com.horizon.banco.exceptions.PessoaNotFoundException;
import com.horizon.banco.repositories.ContaRepository;
import com.horizon.banco.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContaService {
    @Autowired
    private ContaRepository contaRepository;
    private PessoaRepository pessoaRepository;
}
