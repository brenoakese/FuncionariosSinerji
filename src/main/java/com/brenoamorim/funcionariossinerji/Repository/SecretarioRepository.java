package com.brenoamorim.funcionariossinerji.Repository;

import com.brenoamorim.funcionariossinerji.Entity.Secretario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SecretarioRepository {

    private List<Secretario> secretarios;

    public SecretarioRepository() {
        this.secretarios = new ArrayList<Secretario>();
    }

    public void save(Secretario secretario) {
        secretarios.add(secretario);
    }

    public List<Secretario> getSecretarios() {
        return secretarios;
    }

    public Secretario findByNome(String nome) {
        for (Secretario secretario : secretarios) {
            if (secretario.getNome().equals(nome)) {
                return secretario;
            }
        }
        return null;
    }
}
