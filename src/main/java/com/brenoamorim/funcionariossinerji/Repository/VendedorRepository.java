package com.brenoamorim.funcionariossinerji.Repository;

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
}
