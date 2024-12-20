## LiterAlura - Catálogo de Libros

# Bienvenido a LiterAlura, un proyecto que consiste en crear un catálogo interactivo de libros utilizando datos de dominio público de la API de Gutendex. Este proyecto permite buscar, registrar y consultar información sobre libros y autores de manera eficiente, con almacenamiento en una base de datos PostgreSQL.

Características

Búsqueda de libros por título:

Permite buscar libros mediante su título en la API de Gutendex.

Los resultados incluyen el título, autor, idioma y número de descargas.

Búsqueda de libros por autor:

Busca libros asociados con un autor específico.

Registro de libros y autores:

Guarda los libros y autores en una base de datos PostgreSQL.

Evita duplicados comprobando si ya están registrados.

Listados personalizados:

Muestra todos los libros registrados.

Lista todos los autores registrados.

Consulta de estadísticas:

Muestra la cantidad de libros registrados por idioma.

Validaciones avanzadas:

Evita registros duplicados de libros y autores.

Maneja entradas inválidas del usuario para una mejor experiencia.

# Tecnologías Utilizadas

Backend

Java 17: Lenguaje principal del proyecto.

Spring Boot: Framework para el desarrollo de aplicaciones backend.

Spring Data JPA: Para la interacción con la base de datos.

Jackson: Biblioteca para procesar datos en formato JSON.

Base de Datos

PostgreSQL: Base de datos relacional para almacenar información de libros y autores.

API

Gutendex API: Fuente de datos de libros de dominio público.

# Estructura del Proyecto

src/main/java/
├── com.example.literalura
│   ├── LiteraluraApplication.java   # Clase principal para iniciar la aplicación
│   ├── models/
│   │   ├── Autor.java               # Entidad Autor
│   │   ├── Libro.java               # Entidad Libro
│   ├── repositories/
│   │   ├── AutorRepository.java     # Repositorio JPA para la entidad Autor
│   │   ├── LibroRepository.java     # Repositorio JPA para la entidad Libro
│   ├── services/
│   │   ├── LibroService.java        # Lógica del negocio de libros
│   ├── utils/
│   │   ├── ApiClient.java           # Cliente para consumir la API de Gutendex
│   └── controllers/
│       ├── MenuController.java      # Controlador principal del menú de consola

# Cómo Ejecutar el Proyecto

Requisitos Previos

Java 17 o superior instalado en tu máquina.

PostgreSQL instalado y configurado.

Herramienta como IntelliJ IDEA o Eclipse.

Configuración

# Clona el repositorio:

git clone https://github.com/tu-usuario/literalura.git
cd literalura

Configura la base de datos PostgreSQL:

Crea una base de datos llamada literaluradb.

En el archivo application.properties, configura las credenciales:

spring.datasource.url=jdbc:postgresql://localhost:5432/literaluradb
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

# Construye y ejecuta la aplicación:

./mvnw spring-boot:run

Sigue las instrucciones en la consola para interactuar con la aplicación.

Uso del Menú

El menú principal ofrece las siguientes opciones:

Buscar libro por título: Ingresa el título del libro en inglés para buscarlo en la API.

Buscar libros por autor: Ingresa el nombre de un autor para listar los libros relacionados.

Listar todos los libros registrados: Muestra todos los libros guardados en la base de datos.

Listar todos los autores registrados: Muestra todos los autores en la base de datos.

Listar autores vivos en un año: Ingresa un año para listar los autores vivos en ese período.

Mostrar cantidad de libros por idioma: Consulta estadísticas sobre los idiomas de los libros registrados.

Salir: Cierra la aplicación.

Validaciones Importantes

# Títulos en inglés:

Se recomienda ingresar títulos en inglés para una mejor búsqueda en la API de Gutendex.

Evitar duplicados:

La aplicación verifica que no se registren libros o autores repetidos en la base de datos.

Entrada del usuario:

Validaciones para manejar entradas incorrectas o no válidas.

Ejemplo de Uso

Buscar Libro por Título

Entrada del usuario:

Ingrese el título del libro: Pride and Prejudice

# Resultado:

Libro guardado con éxito:
Título: Pride and Prejudice
Autor: Jane Austen
Año de Nacimiento: 1775
Año de Fallecimiento: 1817
Idioma: en
Descargas: 12345

Listar Autores Vivos en un Año

# Entrada del usuario:

Ingrese el año: 1800

Resultado:

Autores vivos en el año 1800:
- Jane Austen (Nacido: 1775)

# Contribuciones

Las contribuciones son bienvenidas. Si encuentras un error o tienes una idea para mejorar el proyecto, por favor abre un issue o un pull request en el repositorio.

# Licencia

Este proyecto se encuentra bajo la Licencia MIT. Puedes usarlo, modificarlo y distribuirlo libremente.

