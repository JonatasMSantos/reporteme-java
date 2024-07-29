# ReporteMe Java

Este é um projeto Java desenvolvido com Spring Boot e Maven, estruturado em um monorepo que contém múltiplos módulos. O projeto foi criado para fornecer funcionalidades de análise de crédito e é composto por dois módulos principais:

1. **rpm-core**: Contém as entidades JPA e repositórios.
2. **rpm-credit-analysis**: Módulo responsável pela lógica de análise de crédito e APIs REST.

## Estrutura do Projeto

```plaintext
reporteme-java/                 # Diretório do projeto pai
│
├── pom.xml                     # POM do projeto pai
│
├── rpm-core/                   # Diretório do módulo rpm-core
│   ├── pom.xml                 # POM do módulo rpm-core
│   └── src/
│       └── main/
│           ├── java/
│           │   └── me/
│           │       └── reporte/
│           │           ├── core/
│           │           │   ├── dto/           # Data Transfer Objects
│           │           │   ├── entity/        # Entidades JPA
│           │           │   └── repository/    # Repositórios JPA
│           └── resources/
│               └── application.properties     # Configurações do módulo core
│
└── rpm-credit-analysis/        # Diretório do módulo rpm-credit-analysis
    ├── pom.xml                 # POM do módulo rpm-credit-analysis
    └── src/
        └── main/
            ├── java/
            │   └── me/
            │       └── reporte/
            │           └── analysis/
            │               ├── controller/    # Controladores REST
            │               ├── service/       # Serviços de negócio
            │               └── config/        # Configurações específicas do módulo
            └── resources/
                └── application.properties     # Configurações do módulo credit-analysis

## Pré-requisitos

Para rodar este projeto, você precisa ter o seguinte instalado na sua máquina:

- **Java 17** ou superior
- **Apache Maven** 3.8.0 ou superior
- **Docker** (para rodar o banco de dados PostgreSQL)

## Configuração do Banco de Dados

O projeto utiliza PostgreSQL como banco de dados. Para facilitar a configuração, usamos o Docker para executar o banco de dados em um container. Execute o seguinte comando para iniciar o banco de dados:

```bash
bashCopiar código
docker run --name postgres -e POSTGRES_PASSWORD=yourpassword -e POSTGRES_DB=reporteme -p 5432:5432 -d postgres

```

Certifique-se de atualizar o `application.properties` com as credenciais corretas:

### `rpm-core/src/main/resources/application.properties`

```
propertiesCopiar código
spring.datasource.url=jdbc:postgresql://localhost:5432/reporteme
spring.datasource.username=postgres
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

```

### `rpm-credit-analysis/src/main/resources/application.properties`

```
propertiesCopiar código
# Configurações do módulo credit-analysis
spring.application.name=rpm-credit-analysis
server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5432/reporteme
spring.datasource.username=postgres
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Configurações de RabbitMQ
rabbitmq.pendingProposal.exchange=pending_proposals_exchange

```

## Como Executar o Projeto

### Passo 1: Construir o Projeto

No diretório raiz do projeto, execute o seguinte comando para construir todos os módulos:

```bash
bashCopiar código
mvn clean install

```

### Passo 2: Executar os Módulos

Cada módulo pode ser executado individualmente. Aqui estão os comandos para executar os dois módulos principais:

### Executar o `rpm-core`

Este módulo não precisa ser executado de forma independente, pois fornece entidades e repositórios para outros módulos.

### Executar o `rpm-credit-analysis`

Navegue até o diretório do módulo e execute:

```bash
bashCopiar código
cd rpm-credit-analysis
mvn spring-boot:run

```

O serviço estará disponível em: `http://localhost:8080`

## Endpoints Disponíveis

O módulo `rpm-credit-analysis` fornece uma API REST para análise de crédito. Aqui estão alguns dos principais endpoints:

### Propostas

- **Criar uma nova proposta**

    ```bash
    bashCopiar código
    POST /api/proposals
    
    ```

  **Corpo da Requisição:**

    ```json
    {
        "name": "John Doe",
        "amount": 50000,
        "term": 12
    }
    
    ```

- **Obter todas as propostas**

    ```bash
    bashCopiar código
    GET /api/proposals
    
    ```

- **Obter uma proposta por ID**

    ```bash
    bashCopiar código
    GET /api/proposals/{id}
    
    ```

- **Atualizar uma proposta**

    ```bash
    bashCopiar código
    PUT /api/proposals/{id}
    
    ```

  **Corpo da Requisição:**

    ```json
    {
        "name": "John Doe",
        "amount": 60000,
        "term": 12
    }
    
    ```

- **Deletar uma proposta**

    ```bash
    bashCopiar código
    DELETE /api/proposals/{id}
    
    ```


## Estrutura do Código

### Módulo `rpm-core`

- **Entidades (`entity`)**: Contém classes Java anotadas com `@Entity`, representando as tabelas do banco de dados.
- **Repositórios (`repository`)**: Interfaces que estendem `JpaRepository`, fornecendo métodos para acessar os dados das entidades.
- **DTOs (`dto`)**: Objetos de Transferência de Dados utilizados para comunicação entre camadas.

### Módulo `rpm-credit-analysis`

- **Controladores (`controller`)**: Classes REST anotadas com `@RestController`, responsáveis por expor endpoints para o frontend ou outros serviços.
- **Serviços (`service`)**: Contém a lógica de negócio, realizando operações necessárias antes de persistir ou manipular dados.
- **Configurações (`config`)**: Arquivos de configuração que ajustam o comportamento do módulo, como a integração com RabbitMQ.

## Integração com RabbitMQ

Este projeto utiliza RabbitMQ para a comunicação assíncrona entre serviços. Aqui está como ele é configurado:

### Configuração do RabbitMQ

No arquivo `application.properties` do `rpm-credit-analysis`, defina as propriedades do RabbitMQ:

```
propertiesCopiar código
rabbitmq.host=localhost
rabbitmq.port=5672
rabbitmq.username=guest
rabbitmq.password=guest

rabbitmq.pendingProposal.exchange=pending_proposals_exchange
rabbitmq.pendingProposal.queue=pending_proposals_queue
rabbitmq.pendingProposal.routingKey=pending.proposal.key

```

### Envio de Mensagens

O `ProposalService` é responsável por enviar notificações de propostas pendentes através do RabbitMQ.

```java
@Service
public class ProposalService {

    private final ProposalRepository proposalRepository;
    private final RabbitMQNotificationService rabbitMQNotificationService;
    private final String exchange;

    public ProposalService(ProposalRepository proposalRepository,
                           RabbitMQNotificationService rabbitMQNotificationService,
                           @Value("${rabbitmq.pendingProposal.exchange}") String exchange) {
        this.proposalRepository = proposalRepository;
        this.rabbitMQNotificationService = rabbitMQNotificationService;
        this.exchange = exchange;
    }

    public Proposal createProposal(Proposal proposal) {
        Proposal savedProposal = proposalRepository.save(proposal);
        rabbitMQNotificationService.notifyPendingProposal(savedProposal, exchange);
        return savedProposal;
    }
}

```

### Consumidor de Mensagens

O consumidor de mensagens processa as notificações recebidas de propostas pendentes.

```java

@Component
public class ProposalConsumer {

    @RabbitListener(queues = "${rabbitmq.pendingProposal.queue}")
    public void receiveMessage(Proposal proposal) {
        System.out.println("Proposta recebida: " + proposal);
        // Processar a proposta
    }
}

```

## Contribuição

Contribuições são bem-vindas! Para contribuir com este projeto, siga as etapas abaixo:

1. Faça um fork do repositório.
2. Crie uma nova branch para suas alterações (`git checkout -b feature/nova-funcionalidade`).
3. Commit suas alterações (`git commit -m 'Adiciona nova funcionalidade'`).
4. Envie para a branch (`git push origin feature/nova-funcionalidade`).
5. Abra um Pull Request.

## Licença

Este projeto é licenciado sob a Licença MIT. Veja o arquivo LICENSE para mais detalhes.

## Suporte

Se você tiver alguma dúvida ou problema, por favor, entre em contato através de jts.msantos@gmail.com