-- Adicionar colunas de status e setor na tabela de motos
ALTER TABLE t_mtu_moto 
ADD COLUMN status VARCHAR(20),
ADD COLUMN setor VARCHAR(10);

-- Atualizar motos existentes com valores padrão
UPDATE t_mtu_moto 
SET status = 'LIVRE', setor = 'A' 
WHERE status IS NULL;

-- Tornar as colunas NOT NULL após popular com valores padrão
ALTER TABLE t_mtu_moto 
ALTER COLUMN status SET NOT NULL,
ALTER COLUMN setor SET NOT NULL;
