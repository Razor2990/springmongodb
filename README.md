
## 📘 Proyecto Spring Boot con MongoDB

Este proyecto es una API REST construida con Spring Boot y conectada a una base de datos MongoDB. A continuación, encontrarás los pasos necesarios para clonar, configurar y ejecutar el proyecto localmente.

##🚀 Requisitos Previos
-- STS IDE / IntelliJ / Netbeans

--Java 17+

--Maven

--MongoDB (puede ser local o en la nube con MongoDB Atlas)

--Git

##🛠️ Clonar el proyecto
bash
Copiar
Editar
git clone https://github.com/Razor2990/springmongodb.git
cd springmongodb
##⚙️ Configurar MongoDB
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
##📦 Construir y ejecutar el proyecto
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
## 📮 Endpoints de ejemplo
Puedes usar herramientas como Postman o curl para probar los endpoints. Algunos ejemplos:

GET /todos

POST /todos

PUT /todos/{id}

DELETE /todos/{id}

## 🔗 API Documentation 📄
La API está documentada con Swagger y puede verse accediendo a:

- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- OpenAPI yml: [http://localhost:8080/v3/api-docs.yml](http://localhost:8080/v3/api-docs.yml)
- Redocly UI: [http://localhost:8080/redoc.html](http://localhost:8080/redoc.html)

## 📈 Health Checks (Spring Boot Actuator)

- Health General: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)
- Health MongoDB: [http://localhost:8080/actuator/health/mongo](http://localhost:8080/actuator/health/mongo)
- Liveness Probe: [http://localhost:8080/actuator/health/liveness](http://localhost:8080/actuator/health/liveness)
- Readiness Probe: [http://localhost:8080/actuator/health/readiness](http://localhost:8080/actuator/health/readiness)

## ⚙️ Otros Endpoints útiles (solo si están expuestos)

- Info: [http://localhost:8080/actuator/info](http://localhost:8080/actuator/info)
- Metrics: [http://localhost:8080/actuator/metrics](http://localhost:8080/actuator/metrics)
- Beans: [http://localhost:8080/actuator/beans](http://localhost:8080/actuator/beans)

## 🧑‍💻 Autor
RazorJs — GitHub
