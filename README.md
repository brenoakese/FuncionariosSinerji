## API Funcionários

## Descrição

API REST desenvolvida em Java para gerenciamento de funcionários e cálculo de salários e benefícios de uma empresa. O sistema possui três tipos de cargos (Secretário, Vendedor e Gerente) com suas respectivas regras de remuneração.

## Tecnologias Utilizadas

Java 21

Spring Boot

Maven (gerenciamento de dependências)

Swagger (documentação da API)

# Estrutura de Cargos e Remuneração

| Cargo     | Salário Base | Adicional por Ano | Benefício                            |
|-----------|--------------|-------------------|--------------------------------------|
| Secretário| R$ 7.000,00  | R$ 1.000,00        | 20% sobre o salário                  |
| Vendedor  | R$ 12.000,00 | R$ 1.800,00        | 30% sobre o valor vendido (R$ 1.000) |
| Gerente   | R$ 20.000,00 | R$ 3.000,00        | Não possui                           |

# Organização de Pacotes e Arquivos

```bash

src/main/java/com/brenoamorim/funcionariossinerji/

├── Controller/

│   └── FuncionariosController.java

├── DTO/

│   └── FuncionarioDTO.java

├── Entity/

│   ├── Data.java

│   ├── Funcionario.java (abstract)

│   ├── Gerente.java

│   ├── Secretario.java

│   └── Vendedor.java

├── Enum/

│   └── Cargo.java

├── Repository/

│   ├── GerenteRepository.java

│   ├── SecretarioRepository.java

│   └── VendedorRepository.java

└── Service/

    ├── GerenteService.java
    
    ├── SecretarioService.java
    
    └── VendedorService.java
```
    
# Como Executar

Pré-requisitos

Java 21 ou superior

Maven 3.8+

Passos para execução

Clone o repositório

```
git clone <url-do-repositorio>
```

```
cd funcionarios-sinerji
```

Compile o projeto

Execute a aplicação

## Executando com Docker

# Pré-requisitos

Docker precisa estar instalado e rodando localmente

# Passos
1. Clone o repositório:
   
```
git clone <url-do-repositorio>
```

```
cd funcionarios-sinerji
```

2. Build da imagem Docker:

```
docker build -t funcionarios-api .
```
```
docker run -p 8080:8080 funcionarios-api
```

Acesse a documentação Swagger

```
http://localhost:8080/swagger-ui.html
```

# Endpoints da API

Base URL: /funcionarios
### 1. POST /salarioTotal
   
Calcula o valor total pago (salário + benefício) a uma lista de funcionários em um mês específico.
Parâmetros:

mes (query param): Mês de referência (1-12)

ano (query param): Ano de referência

Body: Lista de FuncionarioDTO

### 2. POST /salario
   
Calcula o total pago somente em salários (sem benefícios) no mês.

Parâmetros:

mes (query param): Mês de referência (1-12)

ano (query param): Ano de referência

Body: Lista de FuncionarioDTO

### 3. POST /beneficios
   
Calcula o total pago em benefícios para funcionários que recebem benefícios.

Parâmetros:

mes (query param): Mês de referência (1-12)

ano (query param): Ano de referência

Body: Lista de FuncionarioDTO (apenas Secretários e Vendedores)

### 4. POST /maiorSalario/mes
   
Retorna o funcionário que recebeu o maior valor no mês.

Parâmetros:

mes (query param): Mês de referência (1-12)

ano (query param): Ano de referência

Body: Lista de FuncionarioDTO

### 5. POST /maiorBeneficio/mes
   
Retorna o nome do funcionário que recebeu o maior valor em benefícios no mês.

Parâmetros:

mes (query param): Mês de referência (1-12)

ano (query param): Ano de referência

Body: Lista de FuncionarioDTO (apenas Secretários e Vendedores)

### 6. POST /vendedor/maisVendeu/mes
   
Retorna o vendedor que mais vendeu no mês.

Parâmetros:

mes (query param): Mês de referência (1-12)

ano (query param): Ano de referência

Body: Lista de FuncionarioDTO (apenas Vendedores)

# Estrutura do DTO
```
json{
  "nome": "João Silva",
  "cargo": "VENDEDOR",
  "dataContratacao": {
    "mes": 12,
    "ano": 2021
  }
}
```
### Exemplo de Uso
Exemplo de requisição para calcular salário total:

POST http://localhost:8080/funcionarios/salarioTotal?mes=12&ano=2021

```
json[

  {
  
    "nome": "Jorge Carvalho",
    "cargo": "SECRETARIO",
    "dataContratacao": {
      "mes": 1,
      "ano": 2018
      
    }
  },
  
  {
  
    "nome": "Ana Silva",
    "cargo": "VENDEDOR",
    "dataContratacao": {
      "mes": 12,
      "ano": 2021
    }
    
  }
  
]
```
## Observações Importantes

Os Repositorys não são uma interface, pois não há interação com o banco de dados, estão fazendo o papel de persistir os dados em uma lista enquanto a aplicação roda. 


### Limitações Conhecidas


Os Cargos devem ser escritos com letra MAIÚSCULA. Eu sei, isso é algo simples de se resolver. 

OS FUNCIONÁRIOS JÁ SÃO CADASTRADOS PREVIAMENTE NO SISTEMA...

endpoints só vão funcionar se os funcionários forem os mesmos cadastrados no sistema. Verifique a classe DataLoader


### Validações Implementadas

Validação de datas (mês: 1-12, ano: até 2025)
Verificação de cargos válidos para cada endpoint
Tratamento de listas vazias
Validação de funcionários com benefícios

### Melhorias Futuras

 Implementar sistema completo de registro de vendas
 Adicionar persistência em banco de dados
 Implementar autenticação e autorização
 Adicionar testes unitários e de integração
 Melhorar tratamento de exceções
 Implementar cache para consultas frequente
 Ajustes de lógica e na flexibilidade dos valores de entrada dos endPoints
 

👨‍💻 Autor: Breno Amorim

Nota: Este projeto foi desenvolvido como parte de um teste técnico, demonstrando conceitos de Orientação a Objetos, API REST e Spring Boot.
