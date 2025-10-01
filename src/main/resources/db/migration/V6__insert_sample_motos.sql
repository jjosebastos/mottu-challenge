-- Inserir 6 motos de exemplo no Setor A
INSERT INTO t_mtu_moto (id_moto, nr_placa, nm_modelo, nr_chassi, status, setor, id_patio, id_operador) VALUES
(gen_random_uuid(), 'MTA0001', 'MOTTUPOP', 'CHASSI_A1', 'LIVRE', 'A', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTA0002', 'MOTTUSPORT', 'CHASSI_A2', 'PROBLEMA', 'A', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTA0003', 'MOTTUE', 'CHASSI_A3', 'MANUTENCAO', 'A', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTA0004', 'MOTTUPOP', 'CHASSI_A4', 'LIVRE', 'A', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTA0005', 'MOTTUSPORT', 'CHASSI_A5', 'PROBLEMA', 'A', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTA0006', 'MOTTUE', 'CHASSI_A6', 'LIVRE', 'A', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL);

-- Inserir 6 motos de exemplo no Setor B
INSERT INTO t_mtu_moto (id_moto, nr_placa, nm_modelo, nr_chassi, status, setor, id_patio, id_operador) VALUES
(gen_random_uuid(), 'MTB0001', 'MOTTUPOP', 'CHASSI_B1', 'MANUTENCAO', 'B', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTB0002', 'MOTTUSPORT', 'CHASSI_B2', 'LIVRE', 'B', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTB0003', 'MOTTUE', 'CHASSI_B3', 'LIVRE', 'B', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTB0004', 'MOTTUPOP', 'CHASSI_B4', 'PROBLEMA', 'B', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTB0005', 'MOTTUSPORT', 'CHASSI_B5', 'LIVRE', 'B', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTB0006', 'MOTTUE', 'CHASSI_B6', 'MANUTENCAO', 'B', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL);

-- Inserir 6 motos de exemplo no Setor C
INSERT INTO t_mtu_moto (id_moto, nr_placa, nm_modelo, nr_chassi, status, setor, id_patio, id_operador) VALUES
(gen_random_uuid(), 'MTC0001', 'MOTTUPOP', 'CHASSI_C1', 'LIVRE', 'C', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTC0002', 'MOTTUSPORT', 'CHASSI_C2', 'PROBLEMA', 'C', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTC0003', 'MOTTUE', 'CHASSI_C3', 'LIVRE', 'C', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTC0004', 'MOTTUPOP', 'CHASSI_C4', 'LIVRE', 'C', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTC0005', 'MOTTUSPORT', 'CHASSI_C5', 'MANUTENCAO', 'C', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTC0006', 'MOTTUE', 'CHASSI_C6', 'PROBLEMA', 'C', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL);

-- Inserir 6 motos de exemplo no Setor D
INSERT INTO t_mtu_moto (id_moto, nr_placa, nm_modelo, nr_chassi, status, setor, id_patio, id_operador) VALUES
(gen_random_uuid(), 'MTD0001', 'MOTTUPOP', 'CHASSI_D1', 'PROBLEMA', 'D', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTD0002', 'MOTTUSPORT', 'CHASSI_D2', 'LIVRE', 'D', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTD0003', 'MOTTUE', 'CHASSI_D3', 'MANUTENCAO', 'D', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTD0004', 'MOTTUPOP', 'CHASSI_D4', 'LIVRE', 'D', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTD0005', 'MOTTUSPORT', 'CHASSI_D5', 'MANUTENCAO', 'D', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL),
(gen_random_uuid(), 'MTD0006', 'MOTTUE', 'CHASSI_D6', 'LIVRE', 'D', (SELECT id_patio FROM t_mtu_patio LIMIT 1), NULL);
