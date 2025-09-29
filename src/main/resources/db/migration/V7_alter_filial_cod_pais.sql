-- V7_alter_filial_cod_pais.sql

-- 1. Primeiro altere o tipo da coluna para VARCHAR(3)
ALTER TABLE t_mtu_filial ALTER COLUMN cd_pais TYPE VARCHAR(3);

-- 2. Atualize os valores numéricos para códigos de país
UPDATE t_mtu_filial SET cd_pais = 'BR' WHERE cd_pais = '1';
UPDATE t_mtu_filial SET cd_pais = 'MX' WHERE cd_pais = '2';

-- 3. Adicione constraint para garantir valores válidos (opcional)
ALTER TABLE t_mtu_filial 
ADD CONSTRAINT chk_cd_pais_valido 
CHECK (cd_pais IN ('BR', 'MX'));