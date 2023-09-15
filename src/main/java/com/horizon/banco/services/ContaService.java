package com.horizon.banco.services;

import com.horizon.banco.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContaService {
    @Autowired
    private ContaRepository contaRepository;

    // Implemente os métodos relacionados à lógica de negócios para Conta
}
