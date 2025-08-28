CREATE TABLE usuario (
                         id_usuario SERIAL PRIMARY KEY,
                         tipo_usuario INT NOT NULL,
                         nome VARCHAR(100) NOT NULL,
                         cpf VARCHAR(11) NOT NULL,
                         matricula VARCHAR(20) NOT NULL,
                         email VARCHAR(100) NOT NULL,
                         senha VARCHAR(14) NOT NULL,
                         CONSTRAINT unique_cpf UNIQUE (cpf),
                         CONSTRAINT unique_matricula UNIQUE (matricula)
);

CREATE TABLE sala (
                      id_sala SERIAL PRIMARY KEY,
                      nome_sala VARCHAR(50) NOT NULL,
                      tipo_sala VARCHAR(50),
                      predio VARCHAR(100),
                      complemento VARCHAR(100),
                      capacidade INT,
                      ativo BOOLEAN NOT NULL
);

CREATE TABLE agendamentos (
                              id_agendamento SERIAL PRIMARY KEY,
                              data_inicio DATE NOT NULL,
                              data_fim DATE NOT NULL,
                              status VARCHAR(50) NOT NULL,
                              cod_usuario INT NOT NULL,
                              cod_sala INT NOT NULL,
                              FOREIGN KEY (cod_usuario) REFERENCES usuario(id_usuario),
                              FOREIGN KEY (cod_sala) REFERENCES sala(id_sala)
);