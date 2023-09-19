package com.horizon.banco.repositories;

import com.horizon.banco.entities.Conta;
import com.horizon.banco.entities.Pessoa;
import com.horizon.banco.entities.enums.TipoConta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByNumeroAndDigito(String numero, String digito);

    List<Conta> findByPessoaAndTipoConta(Pessoa pessoa, TipoConta tipoConta);

    List<Conta> findByPessoa(Pessoa pessoa);

    List<Conta> findByPessoaId(Long pessoaId);
}
