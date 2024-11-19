-- Inserciones de autor
INSERT INTO autor (biografia, nombre) VALUES
    ('Un escritor famoso de ciencia ficción.', 'Isaac Asimov'),
    ('Experto en historia y filosofía.', 'Yuval Noah Harari'),
    ('Conocido por sus investigaciones en neurociencia.', 'Oliver Sacks');

-- Inserciones de categoria
INSERT INTO categoria (descripcion, nombre_categoria) VALUES
    ('Ficción científica, mundos futuros y avances tecnológicos.', 'Ciencia Ficción'),
    ('Estudios históricos, filosofías y pensamientos de grandes pensadores.', 'Historia y Filosofía'),
    ('Investigación sobre la mente humana y sus procesos cognitivos.', 'Neurociencia');

-- Inserciones de libro
INSERT INTO libro (disponibilidad, id_autorfk, id_categoriafk, descripcion, fecha_publicacion, titulo) VALUES
    (true, 1, 1, 'Un clásico de la ciencia ficción que explora la robótica y la ética en el futuro.', '1950-12-01', 'Yo, Robot'),
    (true, 2, 2, 'Un recorrido a través de la historia humana, desde la prehistoria hasta la modernidad.', '2011-05-15', 'Sapiens: De animales a dioses'),
    (true, 3, 3, 'Estudio profundo sobre las condiciones neurológicas que afectan el comportamiento humano.', '2015-06-30', 'El Hombre que confundió a su Mujer con un Sombrero'),
    (false, 1, 1, 'Un análisis profundo sobre la inteligencia artificial y sus implicaciones futuras.', '1985-03-20', 'El Fin de la Eternidad'),
    (true, 2, 2, 'Un análisis filosófico sobre la moralidad y la ética humana a través del tiempo.', '2014-08-10', 'De Animales a Dioses');