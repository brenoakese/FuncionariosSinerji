package com.brenoamorim.funcionariossinerji.Repository;

import com.brenoamorim.funcionariossinerji.Entity.Gerente;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GerenteRepository {

    private List<Gerente> gerentes;

    public GerenteRepository() {
        this.gerentes = new ArrayList<Gerente>();
    }

    public void save(Gerente gerente) {
        gerentes.add(gerente);
    }

    public List<Gerente> getGerentes() {
        return gerentes;
    }

    public Gerente findByNome(String nome) {
        for (Gerente gerente : gerentes) {
            if (gerente.getNome().equals(nome)) {
                return gerente;
            }
        }
        return null;
    }
}
