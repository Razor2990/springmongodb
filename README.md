
📘 Proyecto Spring Boot con MongoDB

Este proyecto es una API REST construida con Spring Boot y conectada a una base de datos MongoDB. A continuación, encontrarás los pasos necesarios para clonar, configurar y ejecutar el proyecto localmente.

🚀 Requisitos Previos
-- STS IDE / IntelliJ / Netbeans

--Java 17+

--Maven

--MongoDB (puede ser local o en la nube con MongoDB Atlas)

--Git

🛠️ Clonar el proyecto
bash
Copiar
Editar
git clone https://github.com/Razor2990/springmongodb.git
cd springmongodb
⚙️ Configurar MongoDB
Opción 1: Usar MongoDB local
Descarga e instala MongoDB desde: https://www.mongodb.com/try/download/community

Inicia el servicio de MongoDB:

En Windows: busca "MongoDB" en servicios y dale iniciar.

En terminal (si tienes mongod instalado):

bash
Copiar
Editar
mongod --dbpath C:\ruta\al\directorio\data
Por defecto, el proyecto usa la conexión a:

bash
Copiar
Editar
mongodb://localhost:27017/springmongodb
Opción 2: Usar MongoDB Atlas (opcional)
Crea una cuenta en https://cloud.mongodb.com

Crea un cluster, una base de datos y un usuario.

Reemplaza la URL de conexión en application.properties o application.yml:

properties
Copiar
Editar
spring.data.mongodb.uri=mongodb+srv://<user>:<password>@<cluster>.mongodb.net/springmongodb?retryWrites=true&w=majority
📦 Construir y ejecutar el proyecto
bash
Copiar
Editar
mvn clean install
Para ejecutar el proyecto:

bash
Copiar
Editar
mvn spring-boot:run
O con el JAR generado:

bash
Copiar
Editar
java -jar target/springmongodb.jar
📮 Endpoints de ejemplo
Puedes usar herramientas como Postman o curl para probar los endpoints. Algunos ejemplos:

GET /todos

POST /todos

PUT /todos/{id}

DELETE /todos/{id}

📄 Documentación de API
La API está documentada con Swagger y puede verse accediendo a:

bash
Copiar
Editar
http://localhost:8080/swagger-ui.html
🚀 Resultado final
Elemento	Ruta final
JSON OpenAPI	http://localhost:8080/api-docs
Swagger UI	http://localhost:8080/swagger
ReDoc (si lo usas)	http://localhost:8080/redoc.html
Asegúrate de que Swagger esté correctamente configurado en tu pom.xml y clase de configuración.

🧑‍💻 Autor
RazorJs — GitHub
