# notification-api

Repositório para o projeto Notification API

### Sobre o Projeto

Este projeto foi desenvolvido utilizando a versão do Java 17 e o Framework Spring Boot 3.2.4.

### Outras tecnologias, padrões e abordagens utilizadas incluem:

Lombok: para que não seja necessário criar todos os getters and setters manualmente, tornando o código menos verboso.

Liquibase: utilizado para automatizar a execução de scripts de banco de dados (migrations).

Spring Security: foi utilizada uma opção de autenticação básica (Basic Auth) para autenticar nas APIs.

Mapstruct: ferramenta para mapeamento de DTOs e entidades no projeto, evitando assim, a chamada de muitos getters e setters no código, o que o tornaria muito verboso.

Tomcat Embedded: versão do servlet container embutido no Spring Boot. Foi utilizado para facilitar o deploy da aplicação, sem a necessidade de configuração de um server ou servlet container externo.

MySQL: o banco de dados utilizado é o MySQL 8.

Docker: o Docker foi utilizado para facilitar o deploy do banco de dados no ambiente local, dispensando a necessidade de instalação do banco na máquina.

Spring Data: foi utilizado, junto ao design pattern repository, para o desenvolvimento das consultas ao banco de dados.

JPQL: linguagem de consulta pertencente ao JPA e utilizada como padrão ao invés do HQL para evitar retrabalho refatoração das consultas se por acaso fosse necessário realizar a substituição da implementação do padrão JPA, do Hibernate (o qual estou utilizando agora) para o Eclipse Link por exemplo.

Swagger: ferramenta utilizada para disponibilizar a documentação das APIs.

log4j2: ferramenta utilizada para escrever os logs da aplicação.

Arquitetura de camadas: foi utilizada a arquitetura de camadas, sendo que houve a mescla de duas estratégias de organização de código. São elas:
* Package by Layer: abordagem utilizada nas camadas model, repository e resource.
* Package by feature: abordagem utilizada na camada de service.

MVC: design pattern utilizado para o desenvolvimento da API. As camadas de domínio da aplicação (model, repository e DTOs) representam a camada model. A camada de controller (resource) que é um intermediário entre as requisições e as regras de negócio. E mesmo não havendo uma UI, pois numa API RestFUL, a exibição geralmente ocorre no padrão JSON, os conceitos de MVC ainda estão presentes.

### Requisitos

* OpenJDK 17
* Maven 3.6.0 ou superior
* Docker 25.0.3 ou superior

### Configurando o Banco de Dados

Para rodar o banco de dados do MySQL 8 via docker disponível neste projeto via Docker Compose, a partir de um terminal, execute os seguintes passos:

1 - Crie o volume que será utilizado pelo MySQL executando o seguinte comando:

    docker volume create mysql8_data

2 - Execute o banco de dados via docker compose de dentro da pasta raiz do projeto com o comando abaixo:

    docker compose -f mysql8-compose.yml up

### Buildando o Projeto

Para buildar o projeto, basta executar o seguinte comando dentro da pasta raiz do projeto via terminal:

    mvn clean install -DskipTests
    
### Rodando a Aplicação e rodando as migrations

Estamos prontos para rodar a aplicação. Ao rodar o projeto, as migrations de criação da estrutura do banco de dados serão rodadas. Execute o comando abaixo a partir do diretório raiz do projeto via terminal:

    java -jar target/notification-api.jar
    
### Testando a aplicação
    
Primeiramente, acesse aos endpoints do projeto pela ui do Swagger a partir da url abaixo:

    http://localhost:8080/swagger-ui

Digite o usuário e a senha:

    usuário: notification
    senha: notification123
    
Através dessa url, é possível testar a aplicação clicando sobre o endpoint desejado, após isso clique no botão "Try it out", preencha os parâmetros caso seja necessário e clique no botão "Execute".

### Autor

    Renato Daniel Santana Santos