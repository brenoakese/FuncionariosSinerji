package com.brenoamorim.funcionariossinerji.Controller;

import com.brenoamorim.funcionariossinerji.DTO.FuncionarioDTO;
import com.brenoamorim.funcionariossinerji.Entity.*;
import com.brenoamorim.funcionariossinerji.Enum.Cargo;
import com.brenoamorim.funcionariossinerji.Service.GerenteService;
import com.brenoamorim.funcionariossinerji.Service.SecretarioService;
import com.brenoamorim.funcionariossinerji.Service.VendedorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/funcionarios")
public class FuncionariosController {

    @Autowired
    private GerenteService gerenteService;

    @Autowired
    private VendedorService vendedorService;

    @Autowired
    private SecretarioService secretarioService;


    @Operation(summary = "recebe uma lista de funcionários, mês e ano e retorna o valor total\n" +
            "pago (salário e benefício) a esses funcionários no mês. Também Salva os funcionários em uma lista")
    @PostMapping("/salarioTotal")
    public ResponseEntity<List<String>> getSalarioTotal(@RequestBody List<FuncionarioDTO> funcionarios, @RequestParam int mes, @RequestParam int ano) {

        if (funcionarios.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        List<String> salarios = funcionarios.stream()
                .map(funcionarioDTO -> {
                    Funcionario funcionario;


                    switch (funcionarioDTO.cargo()) {
                        case GERENTE ->
                                funcionario = gerenteService.salvarGerente(funcionarioDTO.nome(), funcionarioDTO.dataContratacao().getMes(), funcionarioDTO.dataContratacao().getAno());
                        case VENDEDOR ->
                                funcionario = vendedorService.salvarVendedor(funcionarioDTO.nome(), funcionarioDTO.dataContratacao().getMes(), funcionarioDTO.dataContratacao().getAno());
                        case SECRETARIO ->
                                funcionario = secretarioService.salvarSecretario(funcionarioDTO.nome(), funcionarioDTO.dataContratacao().getMes(), funcionarioDTO.dataContratacao().getAno());
                        default -> throw new IllegalArgumentException("Cargo desconhecido: " + funcionarioDTO.cargo());
                    }

                    return funcionario.getNome() + " - " + funcionario.salarioTotalMes(new Data(mes, ano));
                })
                .toList();

        return ResponseEntity.ok(salarios);
    }

    @Operation(summary = "receba uma lista de funcionários, mês e ano e retorne o total pago\n" +
            "somente em salários no mês. Também Salva os funcionários em uma lista")
    @PostMapping("/salario")
    public ResponseEntity<List<String>> getSalario(@RequestBody List<FuncionarioDTO> funcionarios, @RequestParam int mes, @RequestParam int ano) {

        if (funcionarios.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        List<String> salarios = funcionarios.stream()
                .map(funcionarioDTO -> {
                    try {
                        Funcionario funcionario;
                        switch (funcionarioDTO.cargo()) {
                            case GERENTE -> funcionario = gerenteService.salvarGerente(funcionarioDTO.nome(), funcionarioDTO.dataContratacao().getMes(), funcionarioDTO.dataContratacao().getAno());
                            case VENDEDOR -> funcionario = vendedorService.salvarVendedor(funcionarioDTO.nome(), funcionarioDTO.dataContratacao().getMes(), funcionarioDTO.dataContratacao().getAno());
                            case SECRETARIO -> funcionario = secretarioService.salvarSecretario(funcionarioDTO.nome(), funcionarioDTO.dataContratacao().getMes(), funcionarioDTO.dataContratacao().getAno());
                            default -> throw new IllegalArgumentException("Cargo desconhecido: " + funcionarioDTO.cargo());
                        }
                        return funcionario.getNome() + " - " + funcionario.salarioMes(new Data(mes, ano));
                    } catch (IllegalArgumentException e) {
                        return "Erro ao criar funcionário " + funcionarioDTO.nome() + ": " + e.getMessage();
                    }
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(salarios);
    }

    @Operation(summary = " recebe uma lista somente com os funcionários que recebem\n" +
            "benefícios, mês e ano e retorne o total pago em benefícios no mês.")
    @PostMapping("/beneficios")
    public ResponseEntity<Double> getBeneficios(@RequestBody List<FuncionarioDTO> funcionarios, @RequestParam int mes, @RequestParam int ano) {
        boolean temGerente = funcionarios.stream().anyMatch(funcionarioDTO -> funcionarioDTO.cargo() == Cargo.GERENTE);

        if (funcionarios.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        if (temGerente) {
            return ResponseEntity.badRequest().body(null);
        }

        double totalBeneficios = funcionarios.stream()
                .mapToDouble(funcionarioDTO -> {
                    try {
                        return switch (funcionarioDTO.cargo()) {
                            case VENDEDOR -> vendedorService.getBeneficioMes(funcionarioDTO, mes, ano);
                            case SECRETARIO -> secretarioService.getBeneficioMes(funcionarioDTO, mes, ano);
                            default -> throw new IllegalArgumentException("Cargo desconhecido ou não suportado: " + funcionarioDTO.cargo());
                        };
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro ao calcular benefício: " + e.getMessage());
                        return 0.0;
                    }
                })
                .sum();

        return ResponseEntity.ok(totalBeneficios);
    }

    @Operation(summary = " recebe uma lista de funcionários, mês e ano e retorne o que\n" +
            "recebeu o valor mais alto no mês.")
    @PostMapping("/maiorSalario/mes")
    public ResponseEntity<Funcionario> getMaiorValorMes(@RequestBody List<FuncionarioDTO> funcionarios, @RequestParam int mes, @RequestParam int ano) {
        Funcionario funcionarioComMaiorSalario = null;
        double maiorSalario = 0.0;

        if (funcionarios.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        for (FuncionarioDTO funcionarioDTO : funcionarios) {
            Funcionario funcionario;
            try {
                switch (funcionarioDTO.cargo()) {
                    case GERENTE ->
                            funcionario = gerenteService.salvarGerente(funcionarioDTO.nome(), funcionarioDTO.dataContratacao().getMes(), funcionarioDTO.dataContratacao().getAno());
                    case VENDEDOR -> {
                         funcionario = vendedorService.salvarVendedor(funcionarioDTO.nome(), funcionarioDTO.dataContratacao().getMes(), funcionarioDTO.dataContratacao().getAno());
                        ((Vendedor) funcionario).addVendasMes(new Data(mes, ano), 1000); //Coloquei o valor de 1000 como exemplo, não estava especificado no enunciado como funcionaria a questão dos vendedores

                    }
                    case SECRETARIO ->
                            funcionario = secretarioService.salvarSecretario(funcionarioDTO.nome(), funcionarioDTO.dataContratacao().getMes(), funcionarioDTO.dataContratacao().getAno());
                    default -> throw new IllegalArgumentException("Cargo desconhecido: " + funcionarioDTO.cargo());
                }

                double salario = funcionario.salarioTotalMes(new Data(mes, ano));
                if (salario > maiorSalario) {
                    maiorSalario = salario;
                    funcionarioComMaiorSalario = funcionario;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao criar funcionário " + funcionarioDTO.nome() + ": " + e.getMessage());
            }
        }
       return ResponseEntity.ok().body(funcionarioComMaiorSalario);

    }

    @Operation(summary = "recebe uma lista somente com os funcionários que recebem\n" +
            "benefícios, mês e ano e retorne o nome do funcionário que recebeu o valor mais alto em benefícios no mês.")
    @PostMapping("/maiorBeneficio/mes")
    public ResponseEntity<String> getMaiorBeneficioMes(@RequestBody List<FuncionarioDTO> funcionarios, @RequestParam int mes, @RequestParam int ano) {

        boolean temGerente = funcionarios.stream().anyMatch(funcionarioDTO -> funcionarioDTO.cargo() == Cargo.GERENTE);

        if (temGerente) {
            return ResponseEntity.badRequest().body("Erro: Gerentes não recebem benefícios");
        }


        if (funcionarios.isEmpty()) {
            return ResponseEntity.badRequest().body("Erro: Lista de funcionários está vazia");
        }

        String funcionarioComMaiorBeneficio = null;
        double maiorBeneficio = 0.0;

        for (FuncionarioDTO funcionarioDTO : funcionarios) {
            try {
                double beneficio = switch (funcionarioDTO.cargo()) {
                    case VENDEDOR -> vendedorService.getBeneficioMes(funcionarioDTO, mes, ano);
                    case SECRETARIO -> secretarioService.getBeneficioMes(funcionarioDTO, mes, ano);
                    default ->
                            throw new IllegalArgumentException("Cargo desconhecido ou não suportado: " + funcionarioDTO.cargo());
                };

                if (beneficio > maiorBeneficio) {
                    maiorBeneficio = beneficio;
                    funcionarioComMaiorBeneficio = funcionarioDTO.nome();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao calcular benefício para " + funcionarioDTO.nome() + ": " + e.getMessage());
            }
        }

        if (funcionarioComMaiorBeneficio == null) {
            return ResponseEntity.ok("Nenhum funcionário com benefícios encontrado");
        }

        return ResponseEntity.ok(funcionarioComMaiorBeneficio);
    }

    @Operation(summary = "recebe uma lista de vendedores, mês e ano e retorne o que mais vendeu no mês.")
    @PostMapping("/vendedor/maisVendeu/mes")
    public ResponseEntity<String> getVendedorQueMaisVendeu(@RequestBody List<FuncionarioDTO> vendedores, @RequestParam int mes, @RequestParam int ano) {
        // Verificar se há funcionários que não são vendedores na lista
        boolean naoTemVendedor = vendedores.stream().anyMatch(funcionarioDTO -> funcionarioDTO.cargo() != Cargo.VENDEDOR);

        if (naoTemVendedor) {
            return ResponseEntity.badRequest().body("Erro: Lista deve conter apenas vendedores");
        }


        if (vendedores.isEmpty()) {
            return ResponseEntity.badRequest().body("Erro: Lista de vendedores está vazia");
        }

        String vendedorQueMaisVendeu = null;
        double maiorVenda = 0.0;

        for (FuncionarioDTO vendedorDTO : vendedores) {
            try {

                double vendasMes = vendedorService.getVendasMes(vendedorDTO, mes, ano);

                if (vendasMes > maiorVenda) {
                    maiorVenda = vendasMes;
                    vendedorQueMaisVendeu = vendedorDTO.nome();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao processar vendedor: " + e.getMessage());
            }
        }

        if (vendedorQueMaisVendeu == null) {
            return ResponseEntity.ok("Nenhum vendedor com vendas encontrado no período");
        }

        return ResponseEntity.ok(vendedorQueMaisVendeu + " - Vendas: R$ " + String.format("%.2f", maiorVenda));
    }

    @Operation(summary = "Registra uma venda para um vendedor específico.")
    @PostMapping("/vendedor/registrarVenda")
    public ResponseEntity<String> registrarVenda(@RequestParam String vendedorNome, @RequestParam int mes, @RequestParam int ano, @RequestParam float valorVenda) {
        try {
            vendedorService.registrarVenda(vendedorNome, mes, ano, valorVenda);
            return ResponseEntity.ok("Venda registrada com sucesso para " + vendedorNome);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro ao registrar venda: " + e.getMessage());
        }
    }

    @Operation(summary = "Lista todos os vendedores cadastrados.")
    @GetMapping("/vendedores")
    public ResponseEntity<List<Vendedor>> listarVendedores() {
        try {
            List<Vendedor> vendedores = vendedorService.listarVendedores();
            return ResponseEntity.ok(vendedores);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Lista todos os funcionários cadastrados.")
    @GetMapping("/listar-Funcionarios")
    public ResponseEntity<List<String>> listarFuncionarios() {
        try {
            List<String> funcionarios = new ArrayList<>(vendedorService.listarVendedores().stream()
                    .map(Vendedor::getNome)
                    .toList());

            funcionarios.addAll(secretarioService.listarSecretarios().stream()
                    .map(Secretario::getNome)
                    .toList());

            funcionarios.addAll(gerenteService.listarGerentes().stream()
                    .map(Gerente::getNome)
                    .toList());

            return ResponseEntity.ok(funcionarios);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}






