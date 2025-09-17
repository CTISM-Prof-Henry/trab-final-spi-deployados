INSERT INTO usuario (id_usuario, tipo_usuario, nome, cpf, matricula, email, senha) VALUES
(1, 1, 'Admin Geral', '12345678901', 'ADMIN001', 'admin.geral@escola.br', 'senha12345'),
(2, 2, 'Profa. Ana', '23456789012', 'DOCENTE123', 'ana.docente@escola.br', 'senha54321'),
(3, 3, 'Aluno Pedro', '34567890123', 'ALUNO456', 'pedro.aluno@escola.br', 'senha98765'),
(4, 2, 'Prof. João', '45678901234', 'DOCENTE456', 'joao.docente@escola.br', 'senha112233'),
(5, 3, 'Aluna Maria', '56789012345', 'ALUNO789', 'maria.aluna@escola.br', 'senha445566')
ON CONFLICT (id_usuario) DO NOTHING;
