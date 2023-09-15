package com.horizon.banco.services;

import com.horizon.banco.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    // Implementar os métodos relacionados à lógica de negócios para Pessoa
}
