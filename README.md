# 📦 Projeto: Digitalização de Pátios Mottu

## 📃 Descrição do Projeto

No dinâmico cenário da mobilidade urbana, a gestão de grandes frotas como a da **Mottu** enfrenta desafios significativos. A ausência de um sistema centralizado e em tempo real para monitorar veículos resulta em perda de tempo na localização de motos, ineficiência operacional, aumento de custos e decisões prejudicadas pela falta de dados precisos.

Identificamos essa lacuna e desenvolvemos uma **solução inovadora** para revolucionar o mapeamento geográfico e o rastreamento em tempo real da frota de motos da Mottu.

### ✨ Benefícios

- **Mapa interativo** com áreas delimitadas para cada pátio.
- Rastreamento em **tempo real** com status operacional:
  - Disponível
  - Em uso
  - Em manutenção
  - Aguardando retirada
- Visão clara da **distribuição das motos** em cada local.
- **Eficiência operacional aprimorada**: elimina buscas manuais.
- **Redução de custos** e melhor aproveitamento de recursos.
- Abre espaço para **inovações futuras** na mobilidade urbana.

---

## 👨‍💻 Squad: CodeCrafters

- Nicolas Dobbeck Mendes  
- José Bezerra Bastos Neto  
- Thiago Henry Dias  

---

## ⚙️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **Spring Security com JWT**
- **Spring Data JPA**
- **Maven**
- **Banco de Dados H2 (em memória)**
- **Lombok**
- **JJWT (Java JWT)**
- **Bean Validation**

---

## 🔐 Segurança com Spring Security + JWT

A aplicação utiliza **JSON Web Tokens (JWT)** para autenticação e autorização:

### 🔄 Fluxo de Autenticação

1. O cliente envia `username` e `password` via `POST /api/login`.
2. O sistema gera e retorna um token JWT.
3. Em chamadas futuras, o token deve ser enviado no header:

   ```
   Authorization: Bearer <token>
   ```

---

## 🧱 Criação e Relacionamento de Entidades (UUID)

As entidades utilizam **UUID** como chave primária, com relacionamento **pai-filho**.

### 1️⃣ Criar Entidade Pai (ex: Pátio)

```json
POST /api/patio
{
  "nome": "Pátio Central",
  "descricao": "Área principal",
  "flagAberto": true,
  "filialId": "..."
}
```

Resposta:

```json
{
  "idPatio": "c8206c8c-1e22-48cc-a3b4-9a78f19bb23a",
  "nome": "Pátio Central",
  ...
}
```

### 2️⃣ Criar Entidade Filha (ex: Moto)

```json
POST /api/moto
{
  "placa": "ABC1234",
  "modelo": "Elétrica",
  "chassi": "CH123456789XYZ",
  "patioId": "c8206c8c-1e22-48cc-a3b4-9a78f19bb23a",
  "operadorId": "0894358e-aba1-4d95-85e5-e82cf04730b9"
}
```

---

## 🗂️ Modelagem das Entidades

### 🏢 Filial ↔ Endereço

```java
@Entity
public class Filial {
  @Id @GeneratedValue @UuidGenerator
  private UUID id;
  private String cnpj;
  private String nome;
  private LocalDate dataAbertura;
  private CodPais codPais;

  @OneToMany(mappedBy = "filial", cascade = CascadeType.ALL)
  private List<Endereco> enderecos;
}
```

```java
@Entity
public class Endereco {
  @Id @GeneratedValue @UuidGenerator
  private UUID idEndereco;
  private String logradouro;
  private String numero;
  private String bairro;
  private String cidade;
  private String uf;
  private String cep;
  private String complemento;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "filial_id")
  private Filial filial;
}
```

### 🏍️ Pátio ↔ Moto ↔ Sensor / Manutenção

```java
@Entity
public class Patio {
  @Id @GeneratedValue @UuidGenerator
  private UUID idPatio;
  private String nome;
  private String descricao;
  private Boolean flagAberto;
  private LocalDateTime timestampCreated;
  private LocalDateTime timestampUpdated;

  @ManyToOne
  private Filial filial;

  @OneToMany(mappedBy = "patio", cascade = CascadeType.ALL)
  private List<Moto> motos;
}
```

```java
@Entity
public class Moto {
  @Id @GeneratedValue(strategy = GenerationType.UUID)
  private UUID idMoto;
  private String placa;
  private Modelo modelo;
  private String chassi;
  private Boolean flagAtivo;

  @ManyToOne
  private Patio patio;

  @ManyToOne
  private Operador operador;

  @OneToMany(mappedBy = "moto", cascade = CascadeType.ALL)
  private List<Sensor> sensores;

  @OneToMany(mappedBy = "moto", cascade = CascadeType.ALL)
  private List<Manutencao> manutencoes;
}
```

```java
@Entity
public class Sensor {
  @Id @GeneratedValue @UuidGenerator
  private UUID idSensor;
  private String tipo;
  private String modelo;
  private String fabricante;
  private StatusSensor statusSensor;
  private String versaoFirmware;
  private LocalDateTime dataInstalacao;
  private LocalDate dataCalibracao;
  private LocalDateTime lastSeen;
  private Double signalStrength;
  private Boolean flagAtivo;

  @ManyToOne
  private Moto moto;
}
```

```java
@Entity
public class Manutencao {
  @Id @GeneratedValue @UuidGenerator
  private UUID idManutencao;
  private String tipo;
  private String descricao;
  private Boolean flagAtivo;
  private LocalDateTime timestampCreated;
  private LocalDateTime timestampUpdated;
  private String status;

  @ManyToOne
  private Moto moto;

  @ManyToOne
  private Sensor sensor;
}
```

```java
@Entity
public class PatioEvento {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id_patio_evento" ,
            nullable = false,
            updatable = false,
            length = 36
    )
    private UUID idPatioEvento;
    private Double latitude;
    private Double longitude;
    private String zona;
    private TipoEvento tipoEvento;
    private LocalDateTime timestampEvento;

    @ManyToOne
    @JoinColumn(name = "id_patio")
    private Patio patio;

    @ManyToOne
    @JoinColumn(name = "id_sensor")
    private Sensor sensor;
}
```

```java
@Entity
public class PatioGeom {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "idPatioGeom",nullable = false, updatable = false, length = 36)
    private UUID idPatioGeom;
    private Double latitudeMin;
    private Double longitudeMin;
    private Double latitudeMax;
    private Double longitudeMax;
    private Boolean flagAtivo;
    @ManyToOne
    @JoinColumn(name = "patio")
    private Patio patio;
}

```


---

## ✅ Documentação Interativa (Swagger)

Acesse a documentação automática da API em:

```
http://localhost:8080/swagger-ui/index.html
```

Funcionalidades:

- Navegar por todos os endpoints (`GET`, `POST`, `PUT`, `DELETE`)
- Testar autenticação via JWT
- Visualizar schemas e exemplos
- Entender os relacionamentos entre entidades

---

## 🧪 Testando a API

Utilize **Postman**, **Insomnia** ou **cURL** para testar a aplicação.

### 1️⃣ Criar Usuário

```http
POST /api/users
```

Payload:

```json
{
  "email": "jose@mail.com",
  "password": "12345",
  "role": "ADMIN"
}
```

### 2️⃣ Login e Token

```http
POST /api/login
```

Payload:

```json
{
  "username": "jose@mail.com",
  "password": "12345"
}
```

Resposta:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5..."
}
```

Utilize o token no header das próximas requisições:

```http
Authorization: Bearer <token>
```

---

## 🚀 Execução Local

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em:

```
http://localhost:8080
```

---

## 📌 Observações Finais

Este projeto representa um **salto tecnológico** na gestão de frotas da Mottu, oferecendo controle em tempo real, automação e inteligência de dados. Ele promove a **evolução digital dos pátios** e reforça o compromisso com a **eficiência, segurança e inovação** na mobilidade urbana. Agradecimentos ao Professor João Carlos e Lima da disciplina de Java Advanced.


