package com.brenoamorim.funcionariossinerji.Service;

import com.brenoamorim.funcionariossinerji.Entity.Data;
import com.brenoamorim.funcionariossinerji.Entity.Gerente;
import com.brenoamorim.funcionariossinerji.Entity.Vendedor;
import com.brenoamorim.funcionariossinerji.Repository.GerenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerenteService {

    @Autowired
    private GerenteRepository gerenteRepository;


    public Gerente salvarGerente(String nome, int mes, int ano) {
        try{
         Gerente gerente = new Gerente(nome, new Data(mes, ano));

         gerenteRepository.save(gerente);

         return gerente;
        }catch (IllegalArgumentException e){

            System.out.println("Erro ao salvar Gerente: " + e.getMessage());
        }

        return null;
    }


    public Gerente getGerente(String nome) {
        try{

            Gerente gerente = gerenteRepository.findByNome(nome);

            if (gerente == null) {
                throw new IllegalArgumentException("Gerente não encontrado: " + nome);
            }

            return  gerente;
        }catch (IllegalArgumentException e){
            System.out.println("Erro ao buscar gerente: " + e.getMessage());
        }

        return null;
    }



    public List<Gerente> listarGerentes() {
            return gerenteRepository.getGerentes();
    }
}
