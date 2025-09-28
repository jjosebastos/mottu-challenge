# 🚀 Storyline Completa para Teste no Postman

## 📋 **Configuração Inicial**
- **Base URL**: `http://localhost:8082`
- **Content-Type**: `application/json`

---

## 🎯 **SEQUÊNCIA DE TESTES (Ordem Correta)**

### **1. CRIAR FILIAL** (Pré-requisito)
```
POST http://localhost:8082/filial
Content-Type: application/json

{
  "filialRequests": [
    {
      "nome": "Filial Centro",
      "cnpj": "12345678000199",
      "cdPais": "BRA",
      "dataAbertura": "2020-01-15"
    }
  ]
}
```
**💾 Salve o `idFilial` da resposta para usar nos próximos passos**

---

### **2. CRIAR PÁTIO** (Pré-requisito para motos)
```
POST http://localhost:8082/patio
Content-Type: application/json

{
  "nome": "Pátio Principal",
  "descricao": "Pátio principal da filial centro",
  "flagAberto": "S",
  "idFilial": "ID_DA_FILIAL_CRIADA_ACIMA"
}
```
**💾 Salve o `idPatio` da resposta para usar nos próximos passos**

---

### **3. CRIAR OPERADOR** (Opcional, mas recomendado)
```
POST http://localhost:8082/operador
Content-Type: application/json

{
  "nome": "João Silva",
  "cpf": "12345678901",
  "telefone": "11999999999",
  "email": "joao@email.com",
  "idFilial": "ID_DA_FILIAL_CRIADA_ACIMA"
}
```
**💾 Salve o `idOperador` da resposta**

---

### **4. CRIAR USUÁRIO PARA LOGIN**
```
POST http://localhost:8082/user
Content-Type: application/json

{
  "username": "admin",
  "password": "123456",
  "role": "ADMIN"
}
```

---

### **5. FAZER LOGIN E OBTER TOKEN**
```
POST http://localhost:8082/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}
```
**💾 Salve o `token` da resposta para usar nos headers das próximas requisições**

---

### **6. CRIAR MOTOS COM STATUS E SETOR** (Agora sim!)
```
POST http://localhost:8082/moto
Content-Type: application/json
Authorization: Bearer SEU_TOKEN_AQUI

{
  "placa": "LGC3M52",
  "modelo": "MOTTUPOP",
  "chassi": "CHASSI001",
  "status": "LIVRE",
  "setor": "A",
  "idPatio": "ID_DO_PATIO_CRIADO_ACIMA",
  "idOperador": "ID_DO_OPERADOR_CRIADO_ACIMA"
}
```

**Crie mais algumas motos para testar:**
```
POST http://localhost:8082/moto
Content-Type: application/json
Authorization: Bearer SEU_TOKEN_AQUI

{
  "placa": "ABC1234",
  "modelo": "MOTTUSPORT",
  "chassi": "CHASSI002",
  "status": "PROBLEMA",
  "setor": "A",
  "idPatio": "ID_DO_PATIO_CRIADO_ACIMA"
}
```

```
POST http://localhost:8082/moto
Content-Type: application/json
Authorization: Bearer SEU_TOKEN_AQUI

{
  "placa": "DEF5678",
  "modelo": "MOTTUE",
  "chassi": "CHASSI003",
  "status": "MANUTENCAO",
  "setor": "B",
  "idPatio": "ID_DO_PATIO_CRIADO_ACIMA"
}
```

---

## 🎯 **TESTANDO OS NOVOS ENDPOINTS DO MAPEAMENTO**

### **7. BUSCAR TODOS OS SETORES**
```
GET http://localhost:8082/patio-mapping/setores
Authorization: Bearer SEU_TOKEN_AQUI
```

### **8. BUSCAR MOTOS DO SETOR A**
```
GET http://localhost:8082/patio-mapping/setor/A
Authorization: Bearer SEU_TOKEN_AQUI
```

### **9. BUSCAR MOTOS DO SETOR B**
```
GET http://localhost:8082/patio-mapping/setor/B
Authorization: Bearer SEU_TOKEN_AQUI
```

### **10. BUSCAR DETALHES DE UMA MOTO ESPECÍFICA**
```
GET http://localhost:8082/patio-mapping/moto/ID_DA_MOTO
Authorization: Bearer SEU_TOKEN_AQUI
```

### **11. BUSCAR MOTO POR PLACA**
```
GET http://localhost:8082/patio-mapping/moto/placa/LGC3M52
Authorization: Bearer SEU_TOKEN_AQUI
```

### **12. ATUALIZAR STATUS DE UMA MOTO**
```
PUT http://localhost:8082/patio-mapping/moto/ID_DA_MOTO/status
Content-Type: application/json
Authorization: Bearer SEU_TOKEN_AQUI

{
  "status": "MANUTENCAO"
}
```

**Teste outros status:**
- `"status": "LIVRE"`
- `"status": "PROBLEMA"`

---

## 🔍 **TESTES ADICIONAIS (Opcionais)**

### **13. BUSCAR TODAS AS MOTOS (Endpoint original)**
```
GET http://localhost:8082/moto/all
Authorization: Bearer SEU_TOKEN_AQUI
```

### **14. BUSCAR MOTOS POR MODELO**
```
GET http://localhost:8082/moto/modelo/MOTTUPOP
Authorization: Bearer SEU_TOKEN_AQUI
```

### **15. ATUALIZAR UMA MOTO (Endpoint original)**
```
PUT http://localhost:8082/moto/ID_DA_MOTO
Content-Type: application/json
Authorization: Bearer SEU_TOKEN_AQUI

{
  "placa": "LGC3M52",
  "modelo": "MOTTUPOP",
  "chassi": "CHASSI001",
  "status": "PROBLEMA",
  "setor": "C",
  "idPatio": "ID_DO_PATIO_CRIADO_ACIMA"
}
```

---

## 📊 **O QUE ESPERAR NAS RESPOSTAS**

### **Resposta do Setor:**
```json
{
  "setor": "A",
  "nome": "Setor A",
  "motos": [
    {
      "idMoto": "uuid",
      "placa": "LGC3M52",
      "status": "LIVRE",
      "setor": "A",
      "cor": "Verde"
    }
  ],
  "totalMotos": 1,
  "motosLivres": 1,
  "motosManutencao": 0,
  "motosProblema": 0
}
```

### **Resposta da Moto:**
```json
{
  "idMoto": "uuid",
  "placa": "LGC3M52",
  "status": "LIVRE",
  "setor": "A",
  "cor": "Verde"
}
```

---

## 🚨 **DICAS IMPORTANTES**

1. **Sempre use o token** nos headers das requisições autenticadas
2. **Salve os IDs** das entidades criadas para usar nos próximos passos
3. **Teste os status**: LIVRE (Verde), MANUTENCAO (Amarelo), PROBLEMA (Vermelho)
4. **Teste os setores**: A, B, C, D
5. **Verifique as cores** nas respostas dos endpoints de mapeamento

---

## 🎯 **ORDEM DE DEPENDÊNCIAS**
1. **Filial** → 2. **Pátio** → 3. **Operador** (opcional) → 4. **Usuário** → 5. **Login** → 6. **Motos** → 7. **Testes dos novos endpoints**

**Boa sorte com os testes! 🚀**
