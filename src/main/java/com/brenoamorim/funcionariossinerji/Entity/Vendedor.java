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
    public Double salarioTotalMes(Data data) {
        try {
            int anosDeServico = this.getAnosDeServico(data);

            if (anosDeServico > 0) {

                if (vendasMes.containsKey(data)){
                    var salarioTotalMes = (this.getSalarioFixo() + (anosDeServico * 1800)) + (this.vendasMes.get(data) * BENEFICIO);

                    return salarioTotalMes;
                }

                return this.getSalarioFixo() + (anosDeServico * 1800);
            }

            if (vendasMes.containsKey(data)){
                return this.getSalarioFixo() + (this.vendasMes.get(data) * BENEFICIO);
            }

            return this.getSalarioFixo();

        }catch (IllegalArgumentException e){
            System.out.println("Erro ao calcular salário total do vendedor: " + e.getMessage());
        }
        return null;
    }


    public void addVendasMes(Data data, float valorVenda) {
        if (this.vendasMes.containsKey(data)) {
            this.vendasMes.put(data, vendasMes.get(data) + valorVenda);
        } else {
            this.vendasMes.put(data, valorVenda);
        }
    }

    @Override
    public Double salarioMes(Data data) {
        try{

            int anosDeServico = this.getAnosDeServico(data);

            if (anosDeServico > 0) {
                return this.getSalarioFixo() + (anosDeServico * 1800);
            }

            return this.getSalarioFixo();

        }catch (IllegalArgumentException e){
            System.out.println("Erro ao calcular salário do vendedor: " + e.getMessage());
        }
        return null;
    }

    public double valorTotalBeneficiosMes(Data data) {

        if (vendasMes.containsKey(data)){
            return this.vendasMes.get(data) * BENEFICIO;
        }

        return 0;
    }

    public Map<Data, Float> getVendasMes() {
        return this.vendasMes;
    }
}
