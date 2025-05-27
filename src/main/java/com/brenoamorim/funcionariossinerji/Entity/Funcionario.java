package com.brenoamorim.funcionariossinerji.Entity;

import com.brenoamorim.funcionariossinerji.Enum.Cargo;

import java.time.LocalDate;
import java.time.Period;

public abstract class Funcionario {
    private String nome;

    private Cargo cargo;

    private Data dataContratacao;

    private double salarioFixo;

    public Funcionario(String nome, Cargo cargo, Data dataContratacao) {
        try{
            this.nome = nome;
            this.cargo = cargo;
            this.dataContratacao = dataContratacao;

        }catch (IllegalArgumentException e){
            System.out.println("Erro ao criar funcionário: " + e.getMessage());
        }
    }

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getCargo() {return cargo.getDescricao();}

    public void setCargo(Cargo cargo) {this.cargo = cargo;}

    public double getSalarioFixo() {
        return salarioFixo;
    }

    public void setSalarioFixo(double salarioFixo) {
        if (salarioFixo < 0) {
            throw new IllegalArgumentException("Salário fixo não pode ser negativo.");
        }
        this.salarioFixo = salarioFixo;
    }

    public Data getDataContratacao() {
        return dataContratacao;
    }

    public int getAnosDeServico(Data data) {
        LocalDate dataComparacao = LocalDate.of(
                data.getAno(),
                data.getMes(),
                1
        );
        LocalDate dataContratacao = LocalDate.of(
                this.getDataContratacao().getAno(),
                this.getDataContratacao().getMes(),
                1
        );

        if (dataComparacao.isBefore(dataContratacao)) {
            throw new IllegalArgumentException("A data de comparação não pode ser anterior à data de contratação.");
        }

        Period periodo = Period.between(dataContratacao, dataComparacao);
        int anosDeServico = periodo.getYears();
        return anosDeServico;
    }

    public abstract Double salarioTotalMes(Data data);

    public abstract Double salarioMes(Data data);
}
