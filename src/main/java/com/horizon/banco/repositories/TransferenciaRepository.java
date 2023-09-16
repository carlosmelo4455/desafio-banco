package com.horizon.banco.repositories;

import com.horizon.banco.entities.Conta;
import com.horizon.banco.entities.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
    List<Transferencia> findByContaOrigemOrContaDestino(Conta contaOrigem, Conta contaDestino);

    List<Transferencia> findByData(Date data);
}
