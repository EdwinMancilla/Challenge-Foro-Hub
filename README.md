--ForoHub API REST--

ForoHub es una API REST desarrollada con Spring Boot 3.x y Java 17 que replica el funcionamiento de un foro de manera backend. En un foro, los participantes pueden colocar sus preguntas sobre determinados asuntos y recibir respuestas de otros usuarios, moderadores o profesores. Este proyecto busca replicar esa dinámica, gestionando la relación entre usuarios, tópicos y respuestas.

--DESCRIPCION DEL PROYECTO--

Un foro es un espacio de colaboración y aprendizaje, donde los estudiantes de plataformas como Alura pueden resolver sus dudas sobre cursos y proyectos. ForoHub se enfoca en la creación, gestión y visualización de tópicos y respuestas, con funcionalidades de autenticación y autorización para proteger la información.

Este proyecto tiene como objetivo desarrollar la lógica de backend, incluyendo:

Persistencia de datos en una base de datos relacional.

Relaciones entre usuarios, tópicos y respuestas.

Validaciones según reglas de negocio.

API REST con rutas siguiendo las mejores prácticas de REST.

Documentación de API mediante Swagger.

--FUNCIONALIDADES DE LA API--

La API permite a los usuarios:

CRUD de Tópicos

Crear un nuevo tópico

Listar todos los tópicos

Obtener un tópico específico

Actualizar un tópico

Eliminar un tópico

Gestión de Respuestas

Crear una respuesta a un tópico

Listar respuestas por tópico

Actualizar una respuesta específica

Eliminar una respuesta específica

Usuarios

Listar usuarios (solo campos necesarios: id, nombre, email, login)


Seguridad

Autenticación y autorización para proteger el acceso a la información sensible

--TECNOLOGIAS uTILIZADAS--

Java 17

Spring Boot 3.x

Spring Data JPA (Hibernate)

Maven o Gradle (según configuración del proyecto)

MySQL

Spring Security (JWT)

Swagger para documentación de API

--AUTOR--
EdwiN Mancilla
