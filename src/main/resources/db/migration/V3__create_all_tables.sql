-- habilita extensão para gerar uuids
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- tabela filial (pai)
CREATE TABLE IF NOT EXISTS t_mtu_filial (
  id_filial uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  nm_filial varchar(50) NOT NULL,
  nr_cnpj varchar(18),
  ts_abertura timestamptz DEFAULT now(),
  cd_pais char(2)
);

-- endereço
CREATE TABLE IF NOT EXISTS t_mtu_endereco (
  id_endereco uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  nm_logradouro varchar(50) NOT NULL,
  nm_bairro varchar(30) NOT NULL,
  nm_cidade varchar(30) NOT NULL,
  nm_uf char(2) NOT NULL,
  nr_cep varchar(8) NOT NULL,
  ds_complemento varchar(40),
  tp_endereco varchar(15),
  id_filial uuid,
  CONSTRAINT fk_endereco_filial FOREIGN KEY (id_filial) REFERENCES t_mtu_filial (id_filial) ON UPDATE NO ACTION ON DELETE SET NULL
);

-- telefone
CREATE TABLE IF NOT EXISTS t_mtu_telefone (
  id_telefone uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  nr_ddd char(3) NOT NULL,
  nr_telefone varchar(15) NOT NULL,
  tp_telefone varchar(15) NOT NULL,
  fl_status char(1) NOT NULL,
  id_filial uuid,
  CONSTRAINT fk_telefone_filial FOREIGN KEY (id_filial) REFERENCES t_mtu_filial (id_filial) ON UPDATE NO ACTION ON DELETE CASCADE
);

-- pátio
CREATE TABLE IF NOT EXISTS t_mtu_patio (
  id_patio uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  nm_patio varchar(50) NOT NULL,
  ds_patio varchar(100),
  ts_created timestamptz DEFAULT now(),
  ts_update timestamptz,
  fl_aberto char(1),
  id_filial uuid,
  CONSTRAINT fk_patio_filial FOREIGN KEY (id_filial) REFERENCES t_mtu_filial (id_filial) ON UPDATE NO ACTION ON DELETE SET NULL
);

-- câmera
CREATE TABLE IF NOT EXISTS t_mtu_camera (
  id_camera uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  ds_local varchar(100) NOT NULL,
  nm_camera varchar(50) NOT NULL,
  vl_latitude numeric(9,6) NOT NULL,
  vl_longitude numeric(9,6) NOT NULL,
  dt_registro timestamptz DEFAULT now(),
  id_patio uuid,
  CONSTRAINT fk_camera_patio FOREIGN KEY (id_patio) REFERENCES t_mtu_patio (id_patio) ON UPDATE NO ACTION ON DELETE SET NULL
);

-- operador
CREATE TABLE IF NOT EXISTS t_mtu_operador (
  id_operador uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  nm_operador varchar(50) NOT NULL,
  nr_cpf varchar(14),
  nr_rg varchar(20),
  dt_inscricao timestamptz
);

-- moto
CREATE TABLE IF NOT EXISTS t_mtu_moto (
  id_moto uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  nm_modelo varchar(50) NOT NULL,
  nr_placa varchar(10) NOT NULL,
  nr_chassi varchar(25),
  fl_status char(1) NOT NULL,
  id_operador uuid,
  id_patio uuid,
  CONSTRAINT fk_moto_operador FOREIGN KEY (id_operador) REFERENCES t_mtu_operador (id_operador) ON UPDATE NO ACTION ON DELETE SET NULL,
  CONSTRAINT fk_moto_patio FOREIGN KEY (id_patio) REFERENCES t_mtu_patio (id_patio) ON UPDATE NO ACTION ON DELETE SET NULL
);

-- sensor
CREATE TABLE IF NOT EXISTS t_mtu_sensor (
  id_sensor uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  nm_modelo varchar(50) NOT NULL,
  tp_sensor varchar(20) NOT NULL,
  nm_fabricante varchar(50),
  vs_firmware varchar(30),
  dt_instalacao timestamptz DEFAULT now(),
  dt_calibracao timestamptz,
  id_moto uuid,
  CONSTRAINT fk_sensor_moto FOREIGN KEY (id_moto) REFERENCES t_mtu_moto (id_moto) ON UPDATE NO ACTION ON DELETE CASCADE
);

-- localização de sensor (telemetria) - alta cardinalidade
CREATE TABLE IF NOT EXISTS t_mtu_sensor_localizacao (
  id_sensor_loc uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  dt_registro timestamptz DEFAULT now(),
  vl_latitude numeric(9,6) NOT NULL,
  vl_longitude numeric(9,6) NOT NULL,
  nv_bateria int,
  st_sensor char(1),
  id_sensor uuid,
  id_moto uuid,
  CONSTRAINT fk_sloc_sensor FOREIGN KEY (id_sensor) REFERENCES t_mtu_sensor (id_sensor) ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_sloc_moto FOREIGN KEY (id_moto) REFERENCES t_mtu_moto (id_moto) ON UPDATE NO ACTION ON DELETE CASCADE
);

-- evento no pátio (ex.: moto entrou/saiu, sensor disparou, etc)
CREATE TABLE IF NOT EXISTS t_mtu_patio_evento (
  id_patio_evento uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  nm_zona varchar(50),
  vl_latitude numeric(9,6),
  vl_longitude numeric(9,6),
  tp_evento varchar(20),
  ts_evento timestamptz DEFAULT now(),
  id_patio uuid,
  id_moto uuid,
  id_camera uuid,
  id_sensor uuid,
  CONSTRAINT fk_patioevento_patio FOREIGN KEY (id_patio) REFERENCES t_mtu_patio (id_patio) ON UPDATE NO ACTION ON DELETE SET NULL,
  CONSTRAINT fk_patioevento_moto FOREIGN KEY (id_moto) REFERENCES t_mtu_moto (id_moto) ON UPDATE NO ACTION ON DELETE SET NULL,
  CONSTRAINT fk_patioevento_camera FOREIGN KEY (id_camera) REFERENCES t_mtu_camera (id_camera) ON UPDATE NO ACTION ON DELETE SET NULL,
  CONSTRAINT fk_patioevento_sensor FOREIGN KEY (id_sensor) REFERENCES t_mtu_sensor (id_sensor) ON UPDATE NO ACTION ON DELETE SET NULL
);

-- status de manutenção (catálogo)
CREATE TABLE IF NOT EXISTS t_mtu_status_manutencao (
  id_status uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  ds_status varchar(100) NOT NULL
);

-- manutenção
CREATE TABLE IF NOT EXISTS t_mtu_manutencao (
  id_manutencao uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tp_servico varchar(50),
  ds_servico varchar(255),
  dt_entrada timestamptz DEFAULT now(),
  dt_saida timestamptz,
  id_sensor uuid,
  id_moto uuid,
  id_status uuid,
  CONSTRAINT fk_manutencao_sensor FOREIGN KEY (id_sensor) REFERENCES t_mtu_sensor (id_sensor) ON UPDATE NO ACTION ON DELETE SET NULL,
  CONSTRAINT fk_manutencao_moto FOREIGN KEY (id_moto) REFERENCES t_mtu_moto (id_moto) ON UPDATE NO ACTION ON DELETE SET NULL,
  CONSTRAINT fk_manutencao_status FOREIGN KEY (id_status) REFERENCES t_mtu_status_manutencao (id_status) ON UPDATE NO ACTION ON DELETE SET NULL
);
