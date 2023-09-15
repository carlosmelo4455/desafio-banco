package com.horizon.banco.services;

import com.horizon.banco.repositories.TransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferenciaService {
    @Autowired
    private TransferenciaRepository transferenciaRepository;

    // Implemente os métodos relacionados à lógica de negócios para Transferencia
}
