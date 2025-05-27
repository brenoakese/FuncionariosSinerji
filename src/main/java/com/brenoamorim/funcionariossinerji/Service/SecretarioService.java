package com.brenoamorim.funcionariossinerji.Service;

import com.brenoamorim.funcionariossinerji.DTO.FuncionarioDTO;
import com.brenoamorim.funcionariossinerji.Entity.Data;
import com.brenoamorim.funcionariossinerji.Entity.Secretario;
import com.brenoamorim.funcionariossinerji.Repository.SecretarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecretarioService {

    @Autowired
    private SecretarioRepository secretarioRepository;


    public Secretario salvarSecretario(String nome, int mes, int ano) {

        try{
        Data dataContratacao = new Data(mes, ano);

        Secretario secretario = new Secretario(nome, dataContratacao);

        secretarioRepository.save(secretario);

        return secretario;

        }catch (IllegalArgumentException e){
            System.out.println("Erro ao cadastrar secretario: " + e.getMessage());
        }

        return null;
    }

    public double getBeneficioMes(FuncionarioDTO funcionarioDTO, int dia, int mes) {
        try{
            Secretario secretario = new Secretario(funcionarioDTO.nome(), funcionarioDTO.dataContratacao());
            Data data = new Data(dia, mes);

            return secretario.valorTotalBeneficiosMes(data);

        }catch (IllegalArgumentException e){
            System.out.println("Erro ao calcular beneficio: " + e.getMessage());
            return 0.0;
        }

    }

    public List<Secretario> listarSecretarios() {

        return secretarioRepository.getSecretarios();
    }

    public Secretario getSecretario(String nome) {
        try {
            Secretario secretario = secretarioRepository.findByNome(nome);

            if (secretario == null) {
                throw new IllegalArgumentException("Secretário não encontrado: " + nome);
            }

            return secretario;
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao buscar secretário: " + e.getMessage());
        }

        return null;
    }
}
