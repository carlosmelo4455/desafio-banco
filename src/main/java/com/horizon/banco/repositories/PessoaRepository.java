package com.horizon.banco.repositories;

import com.horizon.banco.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByCpf(String cpf);

    List<Pessoa> findByNome(String nome);

}
