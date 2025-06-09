# GS - API Meteorológica

API RESTful para gerenciamento de cidades e alertas meteorológicos, com integração a dados climáticos externos. Desenvolvida com Spring Boot, JPA, Oracle DB e autenticação via JWT.

## Deploy

A aplicação está publicada em:  
**https://gs1-java.onrender.com**

---

## Autenticação

Todas as requisições (exceto cadastro e login) exigem token JWT.

### Login

**POST** `/auth/login`  
```json
{
  "login": "admin",
  "senha": "123456"
}
```

Resposta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Utilize o token no authorize no swaggewre

---

## Requisições - Cidades

### Listar todas

**GET** `/cidades`

Resposta:
```json
[
  {
    "id": 1,
    "nome": "Recife",
    "estado": "PE",
    "latitude": -8.0476,
    "longitude": -34.877
  }
]
```

---

### Buscar por ID

**GET** `/cidades/{id}`

---

### Criar cidade

**POST** `/cidades`  
```json
{
  "nome": "Fortaleza",
  "estado": "CE",
  "latitude": -3.71722,
  "longitude": -38.5433
}
```

---

### Atualizar cidade

**PUT** `/cidades/{id}`  
```json
{
  "nome": "Fortaleza",
  "estado": "CE",
  "latitude": -3.71722,
  "longitude": -38.5433
}
```

---

### Remover cidade

**DELETE** `/cidades/{id}`

---

## Requisição - Alerta

### Gerar alerta para cidade

**POST** `/alertas/{idCidade}`  
Retorna alerta com base na temperatura da cidade informada.

---

## Tecnologias

- Java 17
- Spring Boot 3.4.x
- Spring Security (JWT)
- Spring Data JPA
- Oracle Database
- Flyway
- Swagger OpenAPI
- Prometheus + Actuator

## Swagger

Documentação interativa disponível em:
```
https://gs1-java.onrender.com/swagger-ui/index.html
```
