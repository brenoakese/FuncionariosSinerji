package com.brenoamorim.funcionariossinerji.Entity;

import com.brenoamorim.funcionariossinerji.Enum.Cargo;

public class Gerente extends Funcionario {


    public Gerente(String nome, Data dataContratacao) {
        super(nome, Cargo.GERENTE, dataContratacao);
        this.setSalarioFixo(20000.00);
    }

    @Override
    public double salarioTotalMes(Data data) {

        int anosDeServico = this.getAnosDeServico(data);

        if (anosDeServico > 0) {
            int adicional = anosDeServico * 3000;

            var salarioTotalMes = this.getSalarioFixo() + adicional;

            return salarioTotalMes;
        }

        return this.getSalarioFixo();
    }

    @Override
    public double salarioMes(Data data) {

        int anosDeServico = getAnosDeServico(data);

        if (anosDeServico > 0) {
            return this.getSalarioFixo() + (anosDeServico * 3000);
        }

        return this.getSalarioFixo();
    }
}