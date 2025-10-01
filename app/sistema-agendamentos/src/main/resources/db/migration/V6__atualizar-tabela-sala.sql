
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'uniq_sala'
    ) THEN
ALTER TABLE sala
    ADD CONSTRAINT uniq_sala UNIQUE (nome_sala, predio, complemento);
END IF;
END$$;


INSERT INTO sala (nome_sala, tipo_sala, predio, complemento, capacidade, ativo)
VALUES
    ('F-109', 'Laboratorio', 'Politético', 'Bloco F', 40, true),
    ('F-207', 'Laboratorio', 'Politético', 'Bloco F', 35, true),
    ('F-209', 'Laboratorio', 'Politético', 'Bloco F', 35, true),
    ('G-109', 'Laboratorio', 'Politético', 'Bloco G', 40, true),
    ('G-209', 'Laboratorio', 'Politético', 'Bloco G', 40, true)
    ON CONFLICT (nome_sala, predio, complemento) DO NOTHING;
