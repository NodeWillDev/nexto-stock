# NexoEstoque

Sistema de controle de estoque, produtos, clientes e vendas, criado com Java, Spring Boot, Spring Data JPA, Thymeleaf e H2. Interface redesenhada em tema escuro.

## Funcionalidades
- Cadastrar, listar, editar e excluir clientes
- Cadastrar, listar, editar e excluir produtos
- Registrar, consultar e editar vendas
- Registrar pagamentos, entregas e descontos
- Banco de dados H2 persistido na pasta `data/`

## Como executar no Windows
1. Instale o **JDK 17 ou superior** e configure o JAVA_HOME.
2. Abra a pasta raiz do projeto em um terminal.
3. Execute `mvnw.cmd spring-boot:run` (o Maven Wrapper baixa o Maven se necessário).
4. Abra http://localhost:8080.

Em Linux/macOS: `./mvnw spring-boot:run`. Também é possível executar `EstoqueApplication.java` na IDE com suporte a Maven.

## Banco de dados
O arquivo de banco é criado em `./data/estoque`. O console H2 é acessível localmente em `/h2-console`. Os dados são mantidos entre inicializações. Não exponha o console H2 publicamente.

## Observações sobre a atualização
O layout, a identidade visual e a página inicial foram renovados, preservando os endpoints e os nomes de campos que os controllers usam. O nome foi alterado apenas na apresentação/metadata, evitando mudanças perigosas nos pacotes Java e no banco já existente.
