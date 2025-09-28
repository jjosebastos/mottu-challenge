# API Documentation - Sistema de Mapeamento do Pátio

## Endpoints para o React Native

### 1. Buscar todos os setores com suas motos
```
GET /patio-mapping/setores
```

**Resposta:**
```json
[
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
    "totalMotos": 25,
    "motosLivres": 15,
    "motosManutencao": 5,
    "motosProblema": 5
  }
]
```

### 2. Buscar motos por setor específico
```
GET /patio-mapping/setor/{setor}
```

**Parâmetros:**
- `setor`: A, B, C ou D

**Resposta:**
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
  "totalMotos": 25,
  "motosLivres": 15,
  "motosManutencao": 5,
  "motosProblema": 5
}
```

### 3. Buscar detalhes de uma moto específica
```
GET /patio-mapping/moto/{id}
```

**Resposta:**
```json
{
  "idMoto": "uuid",
  "placa": "LGC3M52",
  "status": "LIVRE",
  "setor": "A",
  "cor": "Verde"
}
```

### 4. Buscar moto por placa
```
GET /patio-mapping/moto/placa/{placa}
```

**Exemplo:**
```
GET /patio-mapping/moto/placa/LGC3M52
```

**Resposta:**
```json
{
  "idMoto": "uuid",
  "placa": "LGC3M52",
  "status": "LIVRE",
  "setor": "A",
  "cor": "Verde"
}
```

### 5. Atualizar status de uma moto
```
PUT /patio-mapping/moto/{id}/status
```

**Body:**
```json
{
  "status": "MANUTENCAO"
}
```

**Valores possíveis para status:**
- `LIVRE` (Verde)
- `MANUTENCAO` (Amarelo)
- `PROBLEMA` (Vermelho)

**Resposta:**
```json
{
  "idMoto": "uuid",
  "placa": "LGC3M52",
  "status": "MANUTENCAO",
  "setor": "A",
  "cor": "Amarelo"
}
```

## Exemplo de uso no React Native

### Buscar dados do setor A:
```javascript
const response = await fetch('http://localhost:8080/patio-mapping/setor/A');
const setorData = await response.json();

// setorData.motos contém todas as motos do setor A
// setorData.motosLivres, setorData.motosManutencao, setorData.motosProblema contêm as contagens
```

### Atualizar status de uma moto:
```javascript
const response = await fetch(`http://localhost:8080/patio-mapping/moto/${motoId}/status`, {
  method: 'PUT',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    status: 'PROBLEMA'
  })
});

const updatedMoto = await response.json();
```

## Cores dos Status
- **LIVRE**: Verde
- **MANUTENCAO**: Amarelo  
- **PROBLEMA**: Vermelho

## Estrutura dos Setores
- **Setor A**: 25 motos
- **Setor B**: 25 motos
- **Setor C**: 25 motos
- **Setor D**: 25 motos
- **Total**: 100 motos
