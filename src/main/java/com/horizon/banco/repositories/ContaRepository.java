package com.horizon.banco.repositories;

import com.horizon.banco.entities.Conta;
import com.horizon.banco.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByNumeroAndDigito(String numero, String digito);

    Optional<Conta> findByPessoaAndTipoConta(Pessoa pessoa, String tipoConta);

    List<Conta> findByPessoa(Pessoa pessoa);

    List<Conta> findByTipoConta(String tipoConta);
}
