# Desafio Backend PicPay

O sistema é uma api RESTFul que consiste em uma plataforma de pagamento simplificada. Nela é possível o usuário realizar transferências bancárias para outros usuários. Temos 2 tipos de usuários, comun e lojista, ambos possuem uma carteira.

## Regras de negócios

- Usuários podem enviar dinheiro (efetuar transferência) para lojistas e entre usuários;
- Lojistas **só recebem** transferências, não enviam dinheiro para ninguém;
- Validar se o usuário tem saldo antes da transferência;
- A operação de transferência deve ser uma transação (ou seja, revertida em qualquer caso de inconsistência) e o dinheiro deve voltar para a carteira do usuário que envia;
- No recebimento de pagamento, o usuário ou lojista precisa receber notificação (envio de email, sms).

## Autenticação

A api faz autenticação através de token JWT em cada requisição, para obter o token, o usuário precisa criar uma conta (comun ou lojista) e efetuar login no sistema. Quando o usuário informa seu email para o cadastro, o sistema faz uma consulta em uma api externa para validar se é válido ao não.

## Serviços externos

Esta api de trasferência faz uso de 2 serviços externos. Um para realizar a validação do email do usuário. O outro realiza o envio dos emails notificando o emissor e o recebedor da trânsferencia via SMTP.

## Testes

A api possuí 100% de cobertura de testes unitários feitos com JUnit5 e Mockito.

## Endpoints

### Registrar um novo usuário

**POST** - `localhost:8080/auth/register`

Endpoint para criar/registrar um novo usuário.

```java
{
    "firstName": "teste",
    "lastName": "example",
    "document": "111.444.777-33",
    "password": "1234",
    "email": "teste.example@gmail.com",
    "balance": 200,
    "userType": "COMMON",
    "role": "USER"
}

```

**Respostas**:

- `Sucesso` - Caso os dados informados estejam como o esperado e não exista nenhum usuário com tal e-mail ou CPF/CNPJ, o usuário será registrado.
    
    ```java
    {
        "message": "Usuário cadastrado com sucesso!"
    }
    ```
    
- `Erro` - Caso o CPF/CNPJ informados já esteja em uso.
    
    ```java
    {
        "message": "Usuário já cadastrado.",
        "statusCode": 400,
        "fieldErrors": {}
    }
    ```
    
- `Erro` - Caso já existe um usuário cadastrado com tal e-mail.
    
    ```java
    {
        "message": "Esse email já está em uso. Tente utilizar outro email.",
        "statusCode": 400,
        "fieldErrors": {}
    }
    ```
    

---

### Logar no sistema

**POST** - `localhost:8080/auth/login`

Endpoint para efetuar login no sistema.

```java
{
    "email": "teste.example@gmail.com",
    "password": "1234"
}
```

**Respostas:**

- `Sucesso` - Caso os dados informados estejam corretos.
    
    ```java
    {
        "token": "token_de_acesso..."
    }
    ```
    
- `Erro` - Caso o e-mail informado para login não exista..
    
    ```java
    {
        "message": "Usuário com email test_example_123@gmail.com não encontrado.",
        "statusCode": 500,
        "fieldErrors": {}
    }
    ```
    
- `Erro` - Caso a senha informado não seja válida.
    
    ```java
    {
        "message": "Usuário ou senha incorretos.",
        "statusCode": 401,
        "fieldErrors": {}
    }
    ```
    

---

### Efetuar transações

**POST** - `localhost:8080/api/transactions`

Endpoint para realizar uma transferência. Permitido somente para usuário do tipo comum.

```java
{
    "senderId": 1,
    "receiverId": 2,
    "amount": 10
}
```

**Respostas:**

- `Sucesso` - Caso tudo o emissor possua a quantia disponivel e não seja do tipo comerciante.
    
    ```java
    {
        "id": 1,
        "senderId": 1,
        "senderName": "exampleSender",
        "receiverId": 2,
        "receiverName": "exampleReceiver",
        "amount": 10,
        "timestamp": "2024-12-23T13:26:46.5965295"
    }
    ```
    
- `Erro` -  Caso o usuário seja lojista.
    
    ```java
    {
        "message": "Lojistas não podem realizar transações.",
        "statusCode": 401,
        "fieldErrors": {}
    }
    ```
    

---

### Buscar todos os usuários

**GET** - `localhost:8080/api/users` 

Endpoint para buscar todos os usuários do sistema. Permitido somente para admins.

**Respostas:**

- `Sucesso` - Caso haja usuários cadastrados.
    
    ```java
    [
        {
          "firstName": "test1",
          "lastName": "example1",
          "document": "131.444.777-33",
          "balance": 190.00,
          "email": "test1@gmail.com",
          "password": "$2a$10$gpL.os1GQaRgg37yseJ1gOjfVcSEzBghqJM7ZEamq32Gq",
          "userType": "COMMON"
        },
        {
          "firstName": "test2",
          "lastName": "example2",
          "document": "222.555.888-44",
          "balance": 350.50,
          "email": "test2@gmail.com",
          "password": "$2a$10$hJK.mn2POq5RtY8vXs9L4O1QxC3jD2tK8P9ZxR5K7mN2k",
          "userType": "COMMON"
        }
    ]
    ```
    
- Caso não haja nenhum usuário cadastrado será retornado uma lista vazia.
    
    ```java
    []
    ```
    

---

### Buscar todas as transações do usuário

**GET** - `localhost:8080/api/transactions` 

Endpoint para buscar todas as transações que o usuário efetuou. Permitido somente para o próprio usuário.

**Respostas:**

- **`Sucesso`** -  Caso o usuário tenha realizado alguma transação.
    
    ```java
     [
        {
            "id": 1,
            "senderId": 1,
            "senderName": "test1 example1",
            "receiverId": 2,
            "receiverName": "test2 example2",
            "amount": 10.00,
            "timestamp": "2024-12-19T16:15:04.936347"
        }
    ]
    ```
    
- Caso o usuário tenha realizado nenhuma transação, será retornado uma lista vazia.
    
    ```java
    []
    ```
    

## Tecnologias utilizadas

<p>
  <img src="https://github.com/tandpfun/skill-icons/blob/main/icons/Java-Dark.svg" alt="CSS Icon" width="50" height="50">
  <img src="https://github.com/tandpfun/skill-icons/blob/main/icons/Spring-Dark.svg" alt="HTML Icon" width="50" height="50">
  <img src="https://github.com/tandpfun/skill-icons/blob/main/icons/MySQL-Dark.svg" alt="HTML Icon" width="50" height="50">
  <img src="https://icon.icepanel.io/Technology/svg/JUnit.svg" alt="HTML Icon" width="50" height="50">
</p>

## Instruções

### Executar os testes

Para executar os testes, na pasta principal do projeto, execute o comando abaixo:

```java
mvn test
```

### Executar projeto

1. Escolha seu banco de dados e configure-o
2. Configure o serviço de SMTP para fazer o envio de e-mails quando as transferencias forem feitas.
3. Acesso o site [https://www.abstractapi.com/api/email-verification-validation-api](https://www.abstractapi.com/api/email-verification-validation-api) , realize o cadastro, pegue sua chave api e adicione ao arquivo .properties.
4. Será necessário criar um arquivo .properties e adicionar a pasta resource seguindo o exemplo abaixo.
    
    ```java
    spring.application.name=demopicpaydesafio
    
    # DATASOURCE
    spring.datasource.url=jdbc:url_database_mysql
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.username=username_database
    spring.datasource.password=password_database
    spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
    
    # EMAIL SMTP
    spring.mail.host=smtp.gmail.com
    spring.mail.port=port_smtp
    spring.mail.username=your_email
    spring.mail.password=you_password
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true
    
    # API KEY
    abstract.api-key=api_key
    api.security.token.secret=${JWT_SECRET:my-secret-key}
    
    ```
    
5. Execute o comando a seguir na pasta principal do projeto.
    
    ```java
    mvn spring-boot:run
    ```
