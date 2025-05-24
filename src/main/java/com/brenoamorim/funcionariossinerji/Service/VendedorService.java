package com.brenoamorim.funcionariossinerji.Service;

import com.brenoamorim.funcionariossinerji.Entity.Data;
import com.brenoamorim.funcionariossinerji.Entity.Vendedor;
import com.brenoamorim.funcionariossinerji.Repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    public Vendedor salvarVendedor(String nome,int mes, int ano ) {
        try {

            Data dataContratacao = new Data(mes, ano);

            Vendedor vendedor = new Vendedor(nome, dataContratacao);

            vendedorRepository.save(vendedor);

            return vendedor;

        }catch (IllegalArgumentException e){
            System.out.println("Erro ao salvar vendedor: " + e.getMessage());
        }

        return null;
    }
}
