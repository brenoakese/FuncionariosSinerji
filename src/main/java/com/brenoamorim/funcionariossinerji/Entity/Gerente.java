package com.brenoamorim.funcionariossinerji.Entity;

import com.brenoamorim.funcionariossinerji.Enum.Cargo;

import java.sql.SQLOutput;

public class Gerente extends Funcionario {


    public Gerente(String nome, Data dataContratacao) {
        super(nome, Cargo.GERENTE, dataContratacao);
        this.setSalarioFixo(20000.00);
    }

    @Override
    public Double salarioTotalMes(Data data) {

        try{
            int anosDeServico = this.getAnosDeServico(data);

            if (anosDeServico > 0) {
                int adicional = anosDeServico * 3000;

                var salarioTotalMes = this.getSalarioFixo() + adicional;

                return salarioTotalMes;
            }

            return this.getSalarioFixo();

        }catch (IllegalArgumentException e){

            System.out.println("Erro ao calcular salário total do gerente: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Double salarioMes(Data data) {

        int anosDeServico = this.getAnosDeServico(data);

        if (anosDeServico > 0) {
            return this.getSalarioFixo() + (anosDeServico * 3000);
        }

        return this.getSalarioFixo();
    }
}