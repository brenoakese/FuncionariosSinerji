package com.brenoamorim.funcionariossinerji.DTO;

import com.brenoamorim.funcionariossinerji.Entity.Data;
import com.brenoamorim.funcionariossinerji.Enum.Cargo;

public record FuncionarioDTO(String nome, Cargo cargo, Data dataContratacao) {
}
