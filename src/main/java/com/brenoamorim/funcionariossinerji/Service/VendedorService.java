package com.brenoamorim.funcionariossinerji.Service;

import com.brenoamorim.funcionariossinerji.DTO.FuncionarioDTO;
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


    public double getBeneficioMes(FuncionarioDTO funcionarioDTO, int mes, int ano) {
        try {
            Vendedor vendedor = new Vendedor(funcionarioDTO.nome(), funcionarioDTO.dataContratacao());
            Data data = new Data(mes, ano);

            return vendedor.valorTotalBeneficiosMes(data);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao calcular beneficio: " + e.getMessage());
            return 0.0;
        }
    }

    public double getVendasMes(FuncionarioDTO funcionarioDTO, int mes, int ano, float valorVendasMes) {
        try {
            Vendedor vendedor = new Vendedor(funcionarioDTO.nome(), funcionarioDTO.dataContratacao());
            Data data = new Data(mes, ano);


            // Por enquanto usando valor de exemplo como no controller
            vendedor.addVendasMes(data, valorVendasMes);

            return vendedor.getVendasMes().getOrDefault(data, 0.0f);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao obter vendas: " + e.getMessage());
            return 0.0;
        }
    }
}
