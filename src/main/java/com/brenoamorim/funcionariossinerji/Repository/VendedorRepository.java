package com.brenoamorim.funcionariossinerji.Repository;

import com.brenoamorim.funcionariossinerji.Entity.Data;
import com.brenoamorim.funcionariossinerji.Entity.Vendedor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VendedorRepository {

    private List<Vendedor> vendedores;

    public VendedorRepository() {
        this.vendedores = new ArrayList<Vendedor>();
    }

    public void save(Vendedor vendedor) {
        vendedores.add(vendedor);
    }

    public  List<Vendedor> getVendedores() {
        return vendedores;
    }

    public void registrarVenda(String nome, float valor, int mes, int ano) {
        for (Vendedor vendedor : vendedores) {
            if (vendedor.getNome().equals(nome)) {
                vendedor.addVendasMes(new Data(mes, ano),valor);
                return;
            }
        }
        throw new IllegalArgumentException("Vendedor não encontrado: " + nome);
    }
}
