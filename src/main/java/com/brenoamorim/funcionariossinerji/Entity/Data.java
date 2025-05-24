package com.brenoamorim.funcionariossinerji.Entity;

public class Data {

    private int mes;
    private int ano;

    public Data(int mes, int ano) {

        if( (mes < 1 || mes > 12) && (ano < 0 || ano > 2025) ){
            throw new IllegalArgumentException("Data inválida");
        }

        this.mes = mes;
        this.ano = ano;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    @Override
    public String toString() {
        return mes + "/" + ano;
    }

}
