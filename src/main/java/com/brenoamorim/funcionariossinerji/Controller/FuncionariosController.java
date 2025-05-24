package com.brenoamorim.funcionariossinerji.Controller;

import com.brenoamorim.funcionariossinerji.DTO.FuncionarioDTO;
import com.brenoamorim.funcionariossinerji.Entity.Data;
import com.brenoamorim.funcionariossinerji.Entity.Funcionario;
import com.brenoamorim.funcionariossinerji.Service.GerenteService;
import com.brenoamorim.funcionariossinerji.Service.SecretarioService;
import com.brenoamorim.funcionariossinerji.Service.VendedorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            "pago (salário e benefício) a esses funcionários no mês.")
    @PostMapping("/salarioTotal")
    public ResponseEntity<List<String>> getSalarioTotal(@RequestBody List<FuncionarioDTO> funcionarios, @RequestParam int mes, @RequestParam int ano) {
        List<String> salarios = funcionarios.stream()
                .map(funcionarioDTO -> {
                    Funcionario funcionario;


                    switch (funcionarioDTO.cargo()) {
                        case GERENTE -> funcionario = gerenteService.salvarGerente(funcionarioDTO.nome(), funcionarioDTO.dataContratacao().getMes(), funcionarioDTO.dataContratacao().getAno());
                        case VENDEDOR -> funcionario = vendedorService.salvarVendedor(funcionarioDTO.nome(), funcionarioDTO.dataContratacao().getMes(), funcionarioDTO.dataContratacao().getAno());
                        case SECRETARIO -> funcionario = secretarioService.salvarSecretario(funcionarioDTO.nome(), funcionarioDTO.dataContratacao().getMes(), funcionarioDTO.dataContratacao().getAno());
                        default -> throw new IllegalArgumentException("Cargo desconhecido: " + funcionarioDTO.cargo());
                    }

                    return funcionario.getNome() + " - " + funcionario.salarioTotalMes(new Data(mes, ano));
                })
                .toList();

        return ResponseEntity.ok(salarios);
    }


}
