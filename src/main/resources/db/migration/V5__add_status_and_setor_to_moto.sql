-- Adicionar APENAS a coluna de setor na tabela de motos
ALTER TABLE t_mtu_moto 
ADD COLUMN setor VARCHAR(10);

-- Atualizar motos existentes com valores padrão
UPDATE t_mtu_moto 
SET setor = 'A' 
WHERE setor IS NULL;

-- Tornar a coluna NOT NULL após popular com valores padrão
ALTER TABLE t_mtu_moto 
ALTER COLUMN setor SET NOT NULL;