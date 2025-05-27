package com.brenoamorim.funcionariossinerji.Repository;

import com.brenoamorim.funcionariossinerji.Entity.Data;
import com.brenoamorim.funcionariossinerji.Entity.Vendedor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public Double getValorVendaVendedorData(Vendedor vendedor, Data data) {

        for (Vendedor v : vendedores) {
            if (v.getNome().equals(vendedor.getNome())) {
                Map<Data, Float> vendedorMes = v.getVendasMes();

                if (vendedorMes.containsKey(data)) {
                    return vendedorMes.get(data).doubleValue();
                } else {
                    return  0.0;
                }
            }
        }
        return null;
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

    public Vendedor findByNome(String nome) {
        for (Vendedor vendedor : vendedores) {
            if (vendedor.getNome().equals(nome)) {
                return vendedor;
            }
        }
        return null; // Retorna null se o vendedor não for encontrado
    }
}
