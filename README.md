# Desafio Java - Concrete
Criar uma aplicação que exponha uma API RESTful de criação de usuários e login.

## Funções disponiveis na API
 - Criação de usuario
 - Login
 - Visualização de perfil do usuario

## Tecnologias utilizadas
 - Spring boot
 - JPA/Hibernate
 - H2

### Endpoints disponiveis no heroku

|HTTP| Urls                            | 
|----|-----------------------------------------------|
|POST | https://user-api-teste.herokuapp.com/create-user | 
|POST | https://user-api-teste.herokuapp.com/login | 
|GET | https://user-api-teste.herokuapp.com/user/{id} |  


### Jsons para teste 

```
    {
        "name": "João da Silva",
        "email": "joao@silva.org",
        "password": "hunter2",
        "phones": [
            {
                "number": "987654321",
                "ddd": "21"
            }
        ]
    }
```

```
    {
        "name": "Teste",
        "email": "teste@gmail.org",
        "password": "123",
    }
```
