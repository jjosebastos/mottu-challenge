# 📦 Projeto: Digitalização de Patios Mottu
## ☕ Squad: CodeCrafters
## Membros do Projeto 👨‍💻

- Nicolas Dobbeck Mendes  
- José Bezerra Bastos Neto  
- Thiago Henry Dias

## ⚙️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **Maven**
- **Spring Security com JWT**
- **Spring Data JPA**
- **Banco de Dados H2 (memória)**
- **Lombok**
- **JJWT (Java JWT)**
- **Validation**

## 🔐 Segurança com Spring Security e JWT

A aplicação implementa autenticação e autorização utilizando JSON Web Tokens (JWT). O fluxo de segurança é o seguinte:

1. O usuário envia suas credenciais para o endpoint de autenticação.
2. Se as credenciais forem válidas, um JWT é gerado e retornado.
3. O cliente utiliza esse token para acessar endpoints protegidos, enviando-o no cabeçalho `Authorization` como `Bearer <token>`.
4. O backend valida o token em cada requisição e autoriza o acesso conforme as permissões do usuário.

## 🗄️ Configuração de Perfis: H2 e Oracle

A aplicação suporta dois perfis de execução:

### 🔹 Perfil `h2` (desenvolvimento)

- Banco de dados em memória H2.
- Configuração no arquivo `application-h2.properties`.
- Ideal para testes locais e desenvolvimento rápido.




## 🧪 Testando a API

Após iniciar a aplicação, você pode testar os endpoints utilizando ferramentas como Postman ou cURL.

### 🔐 Autenticação

**Endpoint:** `POST /api/login`

**Payload:**

```json
{
  "username": "usuario",
  "password": "senha"
}
```

**Resposta:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6..."
}
```

### 🔒 Acesso a Endpoints Protegidos

Inclua o token JWT no cabeçalho `Authorization`:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...
```


## 🚀 Executando a Aplicação

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   cd seu-repositorio
   ```

2. **Compile o projeto:**

   ```bash
   mvn clean install
   ```

3. **Inicie a aplicação:**

   ```bash
   # Para o perfil H2
   mvn spring-boot:run
   ```
