package com.horizon.banco;

import com.horizon.banco.entities.Conta;
import com.horizon.banco.entities.Pessoa;
import com.horizon.banco.entities.enums.TipoConta;
import com.horizon.banco.repositories.ContaRepository;
import com.horizon.banco.repositories.PessoaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BancoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancoApplication.class, args);
	}
	@Bean
	public CommandLineRunner loadData(
			PessoaRepository pessoaRepository,
			ContaRepository contaRepository) {
		return (args) -> {
			Pessoa p = new Pessoa(1L, "João Silva", "123456789", "123.456.789-00");
			pessoaRepository.save(p);
			Conta c = new Conta(1L,p,"123456","1",100, TipoConta.C);
			contaRepository.save(c);

		};
	}
}
