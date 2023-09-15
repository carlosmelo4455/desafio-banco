package com.horizon.banco.controllers;

import com.horizon.banco.services.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transferencias")
public class TransferenciaController {
    @Autowired
    private TransferenciaService transferenciaService;

    // Implemente os métodos de controle relacionados a Transferencia
}
