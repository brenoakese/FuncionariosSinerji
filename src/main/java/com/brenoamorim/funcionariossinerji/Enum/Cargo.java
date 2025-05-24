package com.brenoamorim.funcionariossinerji.Enum;

public enum Cargo {
    SECRETARIO("Secretário"),
    VENDEDOR("Vendedor"),
    GERENTE("Gerente");


    private String descricao;

    Cargo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
