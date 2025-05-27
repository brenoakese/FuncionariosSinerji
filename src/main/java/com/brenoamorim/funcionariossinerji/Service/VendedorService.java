package com.brenoamorim.funcionariossinerji.Service;

import com.brenoamorim.funcionariossinerji.DTO.FuncionarioDTO;
import com.brenoamorim.funcionariossinerji.Entity.Data;
import com.brenoamorim.funcionariossinerji.Entity.Vendedor;
import com.brenoamorim.funcionariossinerji.Repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public Double getBeneficioMes(FuncionarioDTO funcionarioDTO, int mes, int ano) {
        try {
            Vendedor vendedor = this.getVendedor(funcionarioDTO.nome());

            if (vendedor == null) {
                throw new IllegalArgumentException("Vendedor não encontrado: " + funcionarioDTO.nome());
            }
            Data data = new Data(mes, ano);

            return vendedor.valorTotalBeneficiosMes(data);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao calcular beneficio: " + e.getMessage());
            return null;
        }
    }

    public Double getVendasMes(FuncionarioDTO funcionarioDTO, int mes, int ano) {
        try {

            Vendedor vendedor = vendedorRepository.findByNome(funcionarioDTO.nome());

            if (vendedor == null) {
                throw new IllegalArgumentException("Vendedor não encontrado: " + funcionarioDTO.nome());
            }
            Data data = new Data(mes, ano);

            Double valorVenda = vendedorRepository.getValorVendaVendedorData(vendedor, data);

            return valorVenda;


        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao obter vendas: " + e.getMessage());
            return null;
        }
    }

    public void registrarVenda(FuncionarioDTO vendedorDTO, int mes, int ano, float valorVenda) {

        try {
            Vendedor vendedor = new Vendedor(vendedorDTO.nome(), new Data( vendedorDTO.dataContratacao().getMes(), vendedorDTO.dataContratacao().getAno()));
            Vendedor vendedorCadastrado = vendedorRepository.findByNome(vendedorDTO.nome());

            if(vendedorCadastrado.equals(vendedor)) {
                vendedorRepository.registrarVenda(vendedorCadastrado, valorVenda, mes, ano);
            } else {
                throw new IllegalArgumentException("Vendedor não encontrado: " + vendedorDTO.nome());
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao registrar venda: " + e.getMessage());
        }
    }

    public Vendedor getVendedor(String nome) {
        try{
            Vendedor vendedor = vendedorRepository.findByNome(nome);

            if (vendedor == null) {
                throw new IllegalArgumentException("Vendedor não encontrado: " + nome);
            }

            return  vendedor;

        }catch (IllegalArgumentException e){
            System.out.println("Erro ao obter vendedor: "+ e.getMessage());
        }

        return null;
    }

    public List<Vendedor> listarVendedores() {
            return vendedorRepository.getVendedores();
    }
}
