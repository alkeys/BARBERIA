# Hola - Proyecto de Demostración de Spring Boot

## Descripción

Este es un proyecto de demostración para Spring Boot. Incluye funcionalidades para la autenticación de usuarios y control de acceso basado en roles usando JWT (JSON Web Tokens). También incluye documentación de la API usando Swagger.

## Prerrequisitos

*   Java 25
*   Maven
*   Docker

## Instalación

1.  Clona el repositorio:
    ```bash
    git clone https://github.com/tu-usuario/hola.git
    ```
2.  Navega al directorio del proyecto:
    ```bash
    cd hola
    ```
3.  Instala las dependencias:
    ```bash
    mvn install
    ```

## Base de Datos con Docker

Este proyecto utiliza PostgreSQL como base de datos. Se proporciona un archivo `docker-compose.yml` para facilitar la creación de la base de datos.

1.  Asegúrate de tener Docker y Docker Compose instalados.
2.  Ejecuta el siguiente comando para iniciar la base de datos:
    ```bash
    docker-compose up -d
    ```

La base de datos se creará con el nombre `aviles`, el usuario `admin` y la contraseña `admin`. La propiedad `spring.jpa.hibernate.ddl-auto=update` en `application.properties` se encargará de crear las tablas automáticamente cuando la aplicación se inicie por primera vez.

## Uso

1.  Ejecuta la aplicación:
    ```bash
    mvn spring-boot:run
    ```
2.  La aplicación se ejecutará en `http://localhost:8080`.

## Documentación de la API

La documentación de la API está disponible en `http://localhost:8080/swagger-ui.html`.

## Construido con

*   [Spring Boot](https://spring.io/projects/spring-boot) - El framework web utilizado
*   [Maven](https://maven.apache.org/) - Gestión de dependencias
*   [PostgreSQL](https://www.postgresql.org/) - Base de datos
*   [JWT](https://jwt.io/) - JSON Web Tokens
*   [Swagger](https://swagger.io/) - Documentación de la API