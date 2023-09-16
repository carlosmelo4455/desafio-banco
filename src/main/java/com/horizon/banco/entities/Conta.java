package com.horizon.banco.entities;

import com.horizon.banco.entities.enums.TipoConta;
import jakarta.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Min;

@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @NotBlank(message = "O número da conta é obrigatório")
    private String numero;

    @NotBlank(message = "O dígito da conta é obrigatório")
    private String digito;

    @Min(value = 0, message = "O saldo inicial deve ser no mínimo 0")
    private double saldo;

    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    // Construtor padrão para o Hibernate
    public Conta() {
    }

    public Conta(Pessoa pessoa, String numero, String digito, double saldo, TipoConta tipoConta) {
        this.pessoa = pessoa;
        this.numero = numero;
        this.digito = digito;
        this.saldo = saldo;
        this.tipoConta = tipoConta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDigito() {
        return digito;
    }

    public void setDigito(String digito) {
        this.digito = digito;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }
}