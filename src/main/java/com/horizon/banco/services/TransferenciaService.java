package com.horizon.banco.services;

import com.horizon.banco.entities.Conta;
import com.horizon.banco.entities.Transferencia;
import com.horizon.banco.repositories.ContaRepository;
import com.horizon.banco.repositories.TransferenciaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransferenciaService {
    @Autowired
    private TransferenciaRepository transferenciaRepository;
    @Autowired
    private ContaRepository contaRepository;
    @Transactional
    public void realizarTransferencia(Conta contaOrigem, Conta contaDestino, double valor) {
        // Verifique se a conta de origem tem saldo suficiente
        if (contaOrigem.getSaldo() < valor) {
            throw new RuntimeException("Saldo insuficiente para realizar a transferência");
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
        contaDestino.setSaldo(contaDestino.getSaldo() + valor);

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        Transferencia transferencia = new Transferencia(null, contaOrigem, contaDestino, valor, new Date());
        transferenciaRepository.save(transferencia);
    }
}
