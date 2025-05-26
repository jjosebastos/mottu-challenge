# 📦 Projeto: Digitalização de Pátios Mottu

## Descrição do Projeto 📃

No dinâmico cenário da mobilidade urbana, a gestão de grandes frotas como a da Mottu enfrenta desafios significativos. A ausência de um sistema centralizado e em tempo real para monitorar veículos resulta em perdas de tempo na localização de motos, ineficiência operacional, elevação de custos e decisões prejudicadas pela falta de dados precisos. Nós identificamos essa lacuna e desenvolvemos uma solução inovadora para revolucionar o mapeamento geográfico e o rastreamento em tempo real da sua frota de motos.<br><br>
Nossa solução oferece uma visão clara e dinâmica da distribuição e do status de cada veículo. Imagine ter um mapa interativo onde cada pátio é uma área delimitada e, dentro dela, marcadores visuais indicam a localização exata de cada moto, esteja ela parada ou em movimento. Essa funcionalidade proporciona um rastreamento em tempo real que permite a qualquer operador identificar instantaneamente a moto e sua posição, além de acessar informações cruciais como seu status operacional: se está disponível, em uso, em manutenção ou aguardando retirada. Isso não só facilita o gerenciamento da frota, mas empodera a equipe a visualizar rapidamente a quantidade de motos em cada local, promovendo uma gestão mais proativa e estratégica.<br><br>
A implementação deste sistema representa um avanço significativo para a Mottu, trazendo benefícios tangíveis que impactam diretamente a eficiência e a economia da operação. A eficiência operacional é aprimorada substancialmente, pois o acesso rápido à localização e ao status das motos elimina a necessidade de buscas manuais, agilizando processos como a retirada de veículos e a organização de manutenções. Isso se traduz em uma redução de custos notável, otimizando recursos e respondendo dinamicamente às demandas do mercado.<br><br>
Este projeto vai muito além de um simples sistema de rastreamento; ele é um passo fundamental na evolução da gestão de frotas da Mottu. Ao oferecer uma visão clara e em tempo real de seus ativos, nossa solução capacita a empresa a operar com uma eficiência sem precedentes. Acreditamos que essa capacidade de monitoramento inteligente não só aprimora as operações diárias, mas também abre portas para inovações futuras, contribuindo significativamente para um cenário de mobilidade urbana mais conectado, seguro e eficiente. Com este projeto, a Mottu está pavimentando o caminho para um futuro onde a logística de frotas é mais inteligente e responsiva.

## ☕ Squad: CodeCrafters

### 👨‍💻 Membros do Projeto

* Nicolas Dobbeck Mendes
* José Bezerra Bastos Neto
* Thiago Henry Dias

---

## ⚙️ Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3.x**
* **Spring Security com JWT**
* **Spring Data JPA**
* **Maven**
* **Banco de Dados H2 (memória)**
* **Lombok**
* **JJWT (Java JWT)**
* **Bean Validation**

---

## 🔐 Segurança com Spring Security e JWT

A segurança da aplicação é baseada em autenticação e autorização com **JSON Web Tokens (JWT)**.

**Fluxo de autenticação:**

1. O cliente envia `username` e `password` para `POST /api/login`.
2. Se válidos, o sistema gera e retorna um token JWT.
3. Nas requisições subsequentes, o cliente inclui o token no header:

   ```http
   Authorization: Bearer <token>
   ```
4. O servidor valida o token em cada acesso a endpoints protegidos.

---

## 🔄 Fluxo de Criação e Relacionamento de Entidades (UUIDs)

A modelagem usa **UUID** como chave primária em todas as tabelas e segue o padrão **pai-filho**:

1. **Criar Entidade Pai** (ex: Pátio, Filial):

   * Requisição `POST` ao controller correspondente.
   * UUID gerado no back-end via `@GeneratedValue` + `@UuidGenerator`.
   * Resposta retorna objeto com ID.

   ```json
   POST /api/patio
   {
     "nome": "Pátio Central",
     "descricao": "Área principal",
     "flagAberto": true,
     "filialId": "..."
   }

   // Retorno:
   {
     "idPatio": "c8206c8c-1e22-48cc-a3b4-9a78f19bb23a",
     "nome": "Pátio Central",
     ...
   }
   ```

2. **Criar Entidade Filha** (ex: Moto, Sensor, Manutenção):

   * Usa o UUID da entidade pai no JSON de input.
   * Spring Data JPA mapeia o relacionamento com `@ManyToOne`.

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

3. **Persistência**:

   * O JPA converte JSON → DTO → Entity.
   * Gera `INSERT` com valor de UUID e chave estrangeira referenciando o pai.

---

## 🗂️ Modelos (`@Entity`)

### Filial ↔ Endereço

```java
@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Filial {
  @Id @GeneratedValue @UuidGenerator
  @Column(name = "idFilial", updatable = false, nullable = false, length = 36)
  private UUID id;
  private String cnpj;
  private String nome;
  @Enumerated(EnumType.STRING)
  private CodPais codPais;
  private LocalDate dataAbertura;
  
  @OneToMany(mappedBy = "filial", cascade = CascadeType.ALL)
  private List<Endereco> enderecos;
}

@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Endereco {
  @Id @GeneratedValue @UuidGenerator
  @Column(name = "idEndereco", updatable = false, nullable = false, length = 36)
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
  @JsonBackReference
  private Filial filial;
}
```

### Pátio ↔ Moto ↔ Sensor / Manutenção

```java
@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Patio {
  @Id @GeneratedValue @UuidGenerator
  @Column(name = "idPatio", updatable = false, nullable = false, length = 36)
  private UUID idPatio;
  private String nome;
  private String descricao;
  private Boolean flagAberto;
  private LocalDateTime timestampCreated;
  private LocalDateTime timestampUpdated;

  @ManyToOne
  @JoinColumn(name = "filial")
  private Filial filial;

  @OneToMany(mappedBy = "patio", cascade = CascadeType.ALL)
  private List<Moto> motos;
}

@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Moto {
  @Id @GeneratedValue(strategy = GenerationType.UUID)
  private UUID idMoto;
  private String placa;
  private Modelo modelo;
  private String chassi;
  private Boolean flagAtivo;

  @ManyToOne
  @JoinColumn(name = "patio")
  @JsonBackReference("pa_mo")
  private Patio patio;

  @ManyToOne(optional = true)
  @JoinColumn(name = "operador")
  @JsonBackReference("op_mo")
  private Operador operador;

  @OneToMany(mappedBy = "moto", cascade = CascadeType.ALL)
  private List<Sensor> sensores;

  @OneToMany(mappedBy = "moto", cascade = CascadeType.ALL)
  private List<Manutencao> manutencoes;
}

@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Sensor {
  @Id @GeneratedValue @UuidGenerator
  @Column(name = "id_sensor", updatable = false, nullable = false, length = 36)
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
  @JoinColumn(name = "id_moto")
  private Moto moto;
}

@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Manutencao {
  @Id @GeneratedValue @UuidGenerator
  @Column(name = "idManutencao", updatable = false, nullable = false, length = 36)
  private UUID idManutencao;
  private String tipo;
  private String descricao;
  private Boolean flagAtivo;
  private LocalDateTime timestampCreated;
  private LocalDateTime timestampUpdated;
  private String status;

  @ManyToOne(optional = true)
  @JoinColumn(name = "moto")
  private Moto moto;

  @ManyToOne(optional = true)
  @JoinColumn(name = "sensor")
  private Sensor sensor;
}
```

---


## ✅ Documentação via Swagger

A API está totalmente documentada utilizando o **Swagger UI**, que pode ser acessado ao executar a aplicação e visitar:

```
http://localhost:8080/swagger-ui/index.html
```

Lá você poderá:

* Visualizar todos os endpoints disponíveis.
* Fazer chamadas `GET`, `POST`, `PUT`, `DELETE` diretamente pela interface.
* Entender os schemas das entidades e seus relacionamentos.

## 🧪 Testando a API

Use Postman, Insomnia ou cURL.

### Autenticação

```http
POST /api/login
{
  "username": "usuario",
  "password": "senha"
}
```

### Criar e Relacionar Entidades

1. **Filial + Endereço**: criar filial → usar `id` em `POST /api/endereco`.
2. **Pátio + Moto**: criar pátio → usar `idPatio` em `POST /api/moto`.
3. **Moto + Sensor/Manutenção**: criar moto → usar `idMoto` em `POST /api/sensor` ou `/api/manutencao`.

---

## 🚀 Executando o Projeto

1. Clone o repositório:

   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   cd seu-repositorio
   ```
2. Instale dependências:

   ```bash
   mvn clean install
   ```
3. Execute com perfil H2:

   ```bash
   mvn spring-boot:run -Dspring.profiles.active=h2
   ```

## Imagem do Dockerhub

Este repositório contém a imagem Docker do projeto *mottu-challenge*, publicada no Docker Hub.

## 📦 Imagem disponível em

[https://hub.docker.com/r/dobbeckm/mottu-challenge](https://hub.docker.com/r/dobbeckm/mottu-challenge)

---

## Como executar a imagem

### 1. Baixar a imagem

bash
docker pull dobbeckm/mottu-challenge:latest
docker run -p 8080:8080 dobbeckm/mottu-challenge:latest

Após isso acessa a aplicação em:
bash
http://localhost:8080
