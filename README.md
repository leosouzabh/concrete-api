# Implementação do desafio Java Concrete Solutions
Crie uma aplicação que exponha uma API RESTful de criação de usuários e login.

## Tecnologias utilizadas
* Java 1.7 and Spring Boot 1.5.2
* Database (h2 em memória)
* jdbc-url: `jdbc:h2:mem:test` 

### Compinando e executando 
* Comando para compilar e executar `mvn spring-boot:run`
* Comando para executar a suide de testes: `mvn test`
* URL: `http://localhost:8080/concrete-api`

### Chamadas da API

### 1) Criação de Usuário
Url: `http://localhost:8080/concrete-api/api/usuarios`
Method: `POST`
Data: 
```
{
    "name": "Leo",
    "email": "leosouzabh@gmail.com",
    "password": "leo",
    "phones": [
        {
            "number": "987654321",
            "ddd": "21"
        },{
            "number": "987654321",
            "ddd": "31"
        }
    ]
}
```

#### Retorno quando sucesso
Http Status: 201 (Created)
Json Retorno:
```
{
  "id": "297e800e5af7aaf0015af7ab10270001",
  "name": "Leo",
  "email": "joao@silva2.org",
  "token": "eyJhbGciOiJIUzUxMiJ9.[...]",
  "created": "2017-03-22T20:17:47.559+0000",
  "modified": "2017-03-22T20:17:47.559+0000",
  "ultimoLogin": "2017-03-22T20:17:47.467+0000",
  "phones": [
    {
      "ddd": "31",
      "number": "987654321"
    },
    {
      "ddd": "21",
      "number": "987654321"
    }
  ]
}
```

#### Retorno quando usuário já existe
Http Status: 400 (Bad Request)
Json Retorno:
```
{
  "mensagem": "E-mail já existente"
}
```

### 2) Listagem de Usuário
Url: `http://localhost:8080/concrete-api/api/usuarios`
Method: `GET`
Json Retorno:
```
{
  "content": [
    {
      "id": "297e800e5af7aaf0015af7aaf9f10000",
      "name": "Leo",
      "email": "leosouzabh@gmail.com",
      "token": "eyJhbGciOiJIUzUxMiJ9.[...]",
      "password": "$2a$10$d60r1E0j2xmPPe9HcN0kgeQ2g8EQ1uiOzV6yPMLh3ttbeYl5zVq56",
      "phones": [],
      "created": "2017-03-22T20:17:41.858+0000",
      "modified": "2017-03-22T20:17:41.858+0000",
      "ultimoLogin": null,
      "version": 0
    },
    {...},   
  ],
  "last": true,
  "totalPages": 1,
  "totalElements": 2,
  "size": 20,
  "number": 0,
  "sort": null,
  "numberOfElements": 2,
  "first": true
}
```

### 3) Login de Usuário
Url: `http://localhost:8080/concrete-api/api/usuarios`
Method: `POST`
Data: 
```
{
    "email": "leosouzabh@gmail.com",
    "password": "leo"
}
```

#### Retorno quando sucesso
Http Status: 200 (Ok)
Json retorno:
```
{
	"id": "297e800e5af7b5c6015af7b5e9c20001",
	"name": "Leo",
	"email": "leosouzabh@gmail.org",
	"token": "eyJhbGciOiJIUzUxMiJ9.[...]",
	"created": "2017-03-22T20:29:38.626+0000",
	"modified": "2017-03-22T20:29:40.624+0000",
	"ultimoLogin": "2017-03-22T20:29:40.621+0000",
	"phones": [
		{
			"ddd": "21",
			"number": "987654321"
		},
		{
			"ddd": "31",
			"number": "987`
54321"
		}
	]
}
```

#### Retorno usuário inválido
Http Status: 400 (Bad Request)
Json retorno:
```
{
  "mensagem": "Usuário e/ou senha inválidos"`

}
```
#### Retorno usuário válido e senha inválida
Http Status: 401 (Unauthorized)
Json retorno:
```
{
  "mensagem": "Usuário e/ou senha inválidos"
}
```

### 4) Perfil de Usuário
Url: `http://localhost:8080/concrete-api/api/perfil/[UUID DO USUARIO]`
Method: `GET`
Header: (Authorization: Bearer **[TOKEN DO USUÁRIO]**)

#### Retorno quando token de autenticação válido
Http Status: 200 (Ok)
Json Retorno:  
```
{
  "id": "297e800e5af7be55015af7bf2d9b0001",
  "name": "Leo",
  "email": "leosouzabh@gmail.org",
  "token": "eyJhbGciOiJIUzUxMiJ9.[...]",
  "created": "2017-03-22T20:39:45.819+0000",
  "modified": "2017-03-22T20:39:45.819+0000",
  "ultimoLogin": "2017-03-22T20:39:45.731+0000",
  "phones": [
    {
      "ddd": "21",
      "number": "987654321"
    },
    {
      "ddd": "31",
      "number": "987654`
21"
    }
  ]
}
```
#### Retorno quando token de autenticação inválido
Http Status: 401 (Unauthorized)
Json Retorno:  
```
{
  "mensagem": "Não autorizado"
}
```
*Observação: O status 401 com a mensagem "Não autorizado" é lançado diante de vários cenarios (token não existente na base, token não pertencente ao usuário informado na url, token não cifrado com padrão correto)*

#### Retorno quando token expirado
Http Status: 401 (Unauthorized)
Json Retorno:  
``` 
{
  "mensagem": "Sessão inválida"
}
```
