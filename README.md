# Foro API
Este es un proyecto de API para un foro, desarrollado utilizando Spring Boot 3.4.1 y Java 17. El proyecto fue creado como parte del desafío "Challenge Foro Hub" de Alura. Permite la autenticación de usuarios mediante JSON Web Tokens (JWT), gestión de usuarios, acceso a la base de datos utilizando JPA, y configuraciones de seguridad para proteger las rutas sensibles.

## Tecnologías Utilizadas
Java 17: Lenguaje de programación utilizado.

Spring Boot 3.4.1: Framework para el desarrollo de aplicaciones Java.

Spring Security: Seguridad y autenticación de usuarios utilizando JWT.

Spring Data JPA: Integración con bases de datos mediante Java Persistence API.

MySQL: Base de datos relacional.

Flyway: Herramienta de migraciones para gestionar cambios en la base de datos.

Swagger/OpenAPI: Generación de documentación interactiva para la API.

Lombok: Librería que reduce el código repetitivo (por ejemplo, getters y setters).

JUnit 5: Framework de pruebas unitarias.

Spring Doc: Integración con OpenAPI para la documentación automática de la API.

## Requisitos Previos
Antes de ejecutar este proyecto, asegúrate de tener instaladas las siguientes herramientas:

Java 17 o superior.
Maven para la gestión de dependencias y construcción del proyecto.
MySQL o cualquier base de datos compatible con JDBC.
## Instalación
### 1. Clona el repositorio
Clona el repositorio en tu máquina local:

git clone https://github.com/tu-usuario/foro-api.git
### 2. Configura la base de datos
Accede al archivo src/main/resources/application.properties y configura las credenciales de la base de datos:

spring.datasource.url=jdbc:mysql://localhost/foro?createDatabaseIfNotExist=true

spring.datasource.username=tu_usuario

spring.datasource.password=tu_contraseña
### 3. Construye el proyecto
Construye el proyecto utilizando Maven:

mvn clean install
### 4. Ejecuta la aplicación
Inicia la aplicación con el siguiente comando:

mvn spring-boot:run

La aplicación estará corriendo en http://localhost:8080.

## Rutas de la API
POST /login: Inicia sesión con las credenciales de un usuario y obtiene un JWT.

POST /users: Registra un nuevo usuario.

GET /users: Obtiene la lista de usuarios habilitados (requiere autenticación).

GET /swagger-ui.html: Documentación interactiva de la API generada con Swagger.

## Dependencias
Este proyecto utiliza las siguientes dependencias para su correcto funcionamiento:

### 1 Spring Boot Starter Data JPA
Permite integrar el proyecto con bases de datos mediante la API de Persistencia de Java (JPA), simplificando la gestión de entidades y transacciones.

ArtifactId: spring-boot-starter-data-jpa
### 2 Spring Boot Starter Security
Proporciona las herramientas necesarias para la autenticación y autorización de usuarios. En este proyecto se utiliza JSON Web Tokens (JWT) para manejar la seguridad.

ArtifactId: spring-boot-starter-security
### 3 Spring Boot Starter Validation
Facilita la validación de entradas (como parámetros de la API) utilizando anotaciones estándar de validación en Java.

ArtifactId: spring-boot-starter-validation
### 4 Spring Boot Starter Web
Proporciona soporte para construir aplicaciones web, incluyendo controladores RESTful, conversores de objetos JSON, etc.

ArtifactId: spring-boot-starter-web
### 5 Flyway Core
Herramienta para gestionar las migraciones de base de datos, asegurando que los cambios en la estructura de la base de datos se realicen de manera ordenada y controlada.

ArtifactId: flyway-core
### 6 Flyway MySQL
Proporciona soporte para migraciones específicas en bases de datos MySQL.

ArtifactId: flyway-mysql
###7 MySQL Connector/J
Conector JDBC para integrar MySQL como sistema de gestión de bases de datos.

ArtifactId: mysql-connector-j
Scope: runtime
###8 Lombok
Librería que ayuda a reducir el código repetitivo al generar automáticamente métodos como getters, setters y constructores.

ArtifactId: lombok
Version: 1.18.24
Scope: provided
### 9 Spring Boot Starter Test
Incluye las herramientas necesarias para realizar pruebas unitarias en el proyecto.

ArtifactId: spring-boot-starter-test
Scope: test
### 10 Spring Security Test
Proporciona herramientas para pruebas de seguridad y autenticación, útil para realizar pruebas de integración de los mecanismos de seguridad (como JWT).

ArtifactId: spring-security-test
Scope: test
### 11 SpringDoc OpenAPI Starter
Genera automáticamente documentación interactiva de la API utilizando Swagger/OpenAPI.

ArtifactId: springdoc-openapi-starter-webmvc-ui
Version: 2.1.0
### 12 Java JWT
Librería para generar y verificar JSON Web Tokens (JWT). Se utiliza en este proyecto para manejar la autenticación de los usuarios.

ArtifactId: java-jwt
Version: 4.2.1
##E 13 Validation API
Define las interfaces de validación estándar en Java, utilizadas en conjunto con Spring Boot Starter Validation para validar entradas de datos.

ArtifactId: validation-api
Version: 2.0.1.Final

### Documentación
La documentación de la API está generada automáticamente con Swagger. Puedes acceder a la documentación interactiva visitando la ruta http://localhost:8080/swagger-ui.html.

## Contribuciones
Si deseas contribuir a este proyecto, sigue estos pasos:

1 Haz un fork de este repositorio.

2 Crea una nueva rama (git checkout -b feature-nueva-caracteristica).

3 Realiza tus cambios y realiza commits detallados.

4 Envía un pull request con una descripción clara de lo que has modificado.

¡Toda ayuda es bienvenida!

