package com.brenoamorim.funcionariossinerji.Entity;

import com.brenoamorim.funcionariossinerji.Enum.Cargo;

import java.util.HashMap;
import java.util.Map;

public class Vendedor extends Funcionario {
    private static final float BENEFICIO =  30f /100;

    private Map<Data, Float> vendasMes;

    public Vendedor (String nome, Data dataContratacao) {
        super(nome, Cargo.VENDEDOR, dataContratacao);
        this.setSalarioFixo(12000.00);

        this.vendasMes = new HashMap<>();
    }

    @Override
    public double salarioTotalMes(Data data) {
        int anosDeServico = this.getAnosDeServico(data);

        if (anosDeServico > 0) {

            if (vendasMes.containsKey(data)){
                var salarioTotalMes = (this.getSalarioFixo() + (anosDeServico * 1800)) + (vendasMes.get(data) * BENEFICIO);

                return salarioTotalMes;
            }

            return this.getSalarioFixo() + (anosDeServico * 1800);
        }

        if (vendasMes.containsKey(data)){
            return this.getSalarioFixo() + (vendasMes.get(data) * BENEFICIO);
        }

        return this.getSalarioFixo();
    }


    @Override
    public double salarioMes(Data data) {
        int anosDeServico = this.getAnosDeServico(data);

        if (anosDeServico > 0) {
            return this.getSalarioFixo() + (anosDeServico * 1800);
        }

        return this.getSalarioFixo();
    }

    public double valorTotalBeneficiosMes(Data data) {

        if (vendasMes.containsKey(data)){
            return vendasMes.get(data) * BENEFICIO;
        }

        return 0;
    }
}
