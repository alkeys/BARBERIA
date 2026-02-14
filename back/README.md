# 💈 Sistema de Gestión de Barbería (Backend)

Bienvenido al backend del Sistema de Gestión de Barbería. Esta aplicación robusta y escalable está construida con **Spring Boot** y diseñada para administrar eficientemente las operaciones diarias de una barbería moderna.

## 🚀 Características Principales

- **Gestión de Usuarios y Seguridad**:
  - Autenticación segura mediante **JWT (JSON Web Tokens)**.
  - Control de acceso basado en roles (Admin, Barbero, Cliente).
- **Gestión de Citas**:
  - Programación, reprogramación y cancelación de citas.
  - Validación de disponibilidad de barberos.
- **Gestión de Catálogos**:
  - **Barberos**: Administración de perfiles, horarios y disponibilidad.
  - **Clientes**: Registro e historial de clientes.
  - **Servicios**: Configuración de precios, duración y descripciones.
- **Documentación API**:
  - Interfaz interactiva con **Swagger UI** para probar endpoints.

## 🛠️ Tecnologías Utilizadas

- **Java 25**: Lenguaje de programación principal.
- **Spring Boot 3.x**: Framework para el desarrollo de microservicios y aplicaciones web.
- **Spring Data JPA / Hibernate**: Persistencia y ORM.
- **PostgreSQL**: Base de datos relacional.
- **Docker**: Contenedorización de la base de datos.
- **Maven**: Gestión de dependencias.
- **Lombok**: Reducción de código boilerplate.

## 📂 Estructura del Proyecto

El código está organizado siguiendo las mejores prácticas de arquitectura en capas:

```
src/main/java/com/aviles/pro/one/
├── config/          # Configuraciones (Seguridad, Swagger, CORS)
├── controllers/     # Controladores REST
├── models/          # Entidades JPA (citas, clientes, servicios, users)
├── repositories/    # Interfaces de acceso a datos
├── services/        # Lógica de negocio
└── dto/             # Objetos de Transferencia de Datos
```

## ⚙️ Configuración e Instalación

### Prerrequisitos

- Java 25
- Maven
- Docker y Docker Compose

### Pasos para Ejecutar

1.  **Clonar el repositorio:**

    ```bash
    git clone https://github.com/tu-usuario/barberia-backend.git
    cd barberia-backend
    ```

2.  **Configurar la Base de Datos:**
    Puedes iniciar una instancia de PostgreSQL usando Docker:

    ```bash
    docker-compose up -d
    ```

    > **Nota:** El esquema inicial de la base de datos se encuentra en `sql/base.sql`. Puedes ejecutar este script para crear las tablas manualmente si no usas `spring.jpa.hibernate.ddl-auto=update`.

3.  **Configurar Variables de Entorno (Opcional):**
    Revisa `src/main/resources/application.properties` para ajustar la conexión a la base de datos si es necesario.

4.  **Compilar y Ejecutar:**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

## 📖 Documentación de la API

Una vez que la aplicación esté en ejecución, puedes acceder a la documentación interactiva de la API en:

👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

Desarrollado con ❤️ para optimizar tu negocio.
