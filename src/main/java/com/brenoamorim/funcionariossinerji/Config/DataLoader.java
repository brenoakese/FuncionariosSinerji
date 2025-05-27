package com.brenoamorim.funcionariossinerji.Config;

import com.brenoamorim.funcionariossinerji.Entity.Data;
import com.brenoamorim.funcionariossinerji.Entity.Gerente;
import com.brenoamorim.funcionariossinerji.Entity.Secretario;
import com.brenoamorim.funcionariossinerji.Entity.Vendedor;
import com.brenoamorim.funcionariossinerji.Repository.GerenteRepository;
import com.brenoamorim.funcionariossinerji.Repository.SecretarioRepository;
import com.brenoamorim.funcionariossinerji.Repository.VendedorRepository;
import com.brenoamorim.funcionariossinerji.Service.GerenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {


    private final GerenteRepository gerenteRepository;


    private final VendedorRepository vendedorRepository;


    private final SecretarioRepository secretarioRepository;

    public DataLoader(GerenteRepository gerenteRepo,
                       VendedorRepository vendedorRepo,
                       SecretarioRepository secretarioRepo) {
        this.gerenteRepository = gerenteRepo;
        this.vendedorRepository = vendedorRepo;
        this.secretarioRepository = secretarioRepo;
    }

    @Override
    public void run(String... args) throws Exception {

        Vendedor ana = new Vendedor("Ana Silva",new Data(12,2021));
        Vendedor joao = new Vendedor("João Mendes",new Data(12,2021));

        secretarioRepository.save(new Secretario("Jorge Carvalho",new Data(1,2018)));
        secretarioRepository.save(new Secretario("Maria Souza",new Data(12,2015)));
        vendedorRepository.save(ana);
        vendedorRepository.save(joao);
        gerenteRepository.save(new Gerente("Juliana Alves",new Data(7,2017)));
        gerenteRepository.save(new Gerente("Bento Albino",new Data(3,2014)));



        vendedorRepository.registrarVenda(ana, 5200, 12, 2021);
        vendedorRepository.registrarVenda(joao, 3400, 12, 2021);
        vendedorRepository.registrarVenda(ana, 4000, 1, 2022);
        vendedorRepository.registrarVenda(joao, 7700, 1, 2022);
        vendedorRepository.registrarVenda(ana, 4200, 2, 2022);
        vendedorRepository.registrarVenda(joao, 5000, 2, 2022);
        vendedorRepository.registrarVenda(joao, 6850, 3, 2022);
        vendedorRepository.registrarVenda(joao, 5900, 3, 2022);
        vendedorRepository.registrarVenda(ana, 7000, 4, 2022);
        vendedorRepository.registrarVenda(joao, 6500, 4, 2022);


        System.out.println("Dados iniciais carregados com sucesso!");

    }
}
