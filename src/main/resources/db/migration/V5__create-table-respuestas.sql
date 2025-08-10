CREATE TABLE respuestas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensaje TEXT NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    solucion BOOLEAN DEFAULT FALSE,
    autor_id BIGINT NOT NULL,
    topico_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (autor_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (topico_id) REFERENCES topicos(id) ON DELETE CASCADE
);