package com.brenoamorim.funcionariossinerji.Entity;

import com.brenoamorim.funcionariossinerji.Enum.Cargo;

public class Secretario extends Funcionario{
    private static final float BENEFICIO =  20f /100;


    public Secretario(String nome, Data dataContratacao) {
        super(nome, Cargo.SECRETARIO, dataContratacao);
        this.setSalarioFixo(7000.00);


    }

    public double salarioMes(Data data) {

        int anosDeServico = this.getAnosDeServico(data);

        if (anosDeServico > 0) {
            return this.getSalarioFixo() + (anosDeServico * 1000);
        }

        return this.getSalarioFixo();
    }


    @Override
    public double salarioTotalMes(Data data) {
        int anosDeServico = this.getAnosDeServico(data);

        if (anosDeServico > 0) {
            var salarioTotalMes = this.getSalarioFixo() + (anosDeServico * 1000);

            salarioTotalMes += salarioTotalMes * BENEFICIO;

            this.setSalarioFixo(salarioTotalMes);

            return salarioTotalMes;
        }

        return this.getSalarioFixo() * (1 + BENEFICIO);

    }


    public double valorTotalBeneficiosMes(Data data) {

        int anosDeServico = this.getAnosDeServico(data);

        System.out.println("Anos de serviço: " + anosDeServico);

        if (anosDeServico > 0) {

            return (this.getSalarioFixo() + (anosDeServico * 1000)) * BENEFICIO;
        }

        return 0;
    }

}
