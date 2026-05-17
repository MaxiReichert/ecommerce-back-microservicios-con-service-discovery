# ecommerce-back-microservicios (con Service Discovery)

Backend de una aplicación de ecommerce (tienda de comida) implementado como arquitectura de **microservicios** con **Java 17**, **Spring Boot 3.5.14** y **Netflix Eureka** para service discovery. Cada microservicio es una aplicación independiente con su propia base de datos H2 en memoria.

---

## Tabla de contenidos

- [Arquitectura](#arquitectura)
- [Tecnologías](#tecnologías)
- [Requisitos previos](#requisitos-previos)
  - [1. Instalar Java 17](#1-instalar-java-17)
  - [2. Instalar Maven](#2-instalar-maven)
- [Levantar los servicios](#levantar-los-servicios)
- [Bases de datos H2](#bases-de-datos-h2)

---

## Arquitectura

```
┌─────────────────────────────────────────────────────┐
│           Eureka Server  :8761                      │
│     (registro y descubrimiento de servicios)        │
└──────────┬──────────┬──────────────┬────────────────┘
           │          │              │
    registra  registra   registra   registra
           │          │              │
┌──────────┴──────────┴──────────────┴────────────────┐
│              Gateway  :8080                         │
│  /api/productos/**  →  lb://ms-producto             │
│  /api/pagos/**      →  lb://ms-pago                 │
│  /api/descuentos/** →  lb://ms-pago                 │
│  /api/pedidos/**    →  lb://ms-pedido               │
└──────────┬──────────┬──────────────┬────────────────┘
           │          │              │
     :8081 │    :8082 │        :8083 │
    ms-producto   ms-pago       ms-pedido
    (H2: productodb) (H2: pagodb) (H2: pedidodb)
```

El Gateway resuelve las direcciones de los microservicios dinámicamente a través de Eureka (prefijo `lb://`), sin necesidad de IPs o puertos hardcodeados.

---

## Tecnologías

| Componente | Tecnología | Puerto |
|---|---|---|
| Eureka Server | Spring Cloud Netflix Eureka | 8761 |
| Gateway | Spring Cloud Gateway 3.5.4 | 8080 |
| ms-producto | Spring Boot 3.5.14 + H2 | 8081 |
| ms-pago | Spring Boot 3.5.14 + H2 | 8082 |
| ms-pedido | Spring Boot 3.5.14 + H2 | 8083 |

---

## Requisitos previos

### 1. Instalar Java 17

> **Verificar si ya lo tenés instalado** (PowerShell o CMD):
> ```powershell
> java -version
> ```
> Si la salida muestra `openjdk 17` o `java version "17"`, podés saltar este paso.

1. Ir a [https://openjdk.org/install/](https://openjdk.org/install/) y descargar el `.zip` de **Java 17** para **Windows x64**.
2. Descomprimir el `.zip` en una carpeta fija, por ejemplo:
   ```
   C:\java\jdk-17
   ```
3. Configurar las variables de entorno:
   - Abrir el menú inicio y buscar **"Editar las variables de entorno del sistema"**.
   - Hacer clic en **"Variables de entorno..."**.
   - En la sección **Variables del sistema**, hacer clic en **"Nueva..."** y completar:
     - Nombre: `JAVA_HOME`
     - Valor: `C:\java\jdk-17`
   - Luego, seleccionar la variable **`Path`**, hacer clic en **"Editar..."** y agregar una nueva entrada:
     - `%JAVA_HOME%\bin`
4. Aceptar todos los cuadros de diálogo, **abrir una nueva terminal** y verificar:
   ```powershell
   java -version
   # Esperado: openjdk version "17.x.x" ...
   ```

---

### 2. Instalar Maven

> Cada proyecto incluye el wrapper **`mvnw.cmd`**, por lo que **Maven no es obligatorio** — podés usarlo directamente (ver [Levantar los servicios](#levantar-los-servicios)).  
> Si preferís tener Maven instalado globalmente, seguí estos pasos:

> **Verificar si ya lo tenés instalado** (PowerShell o CMD):
> ```powershell
> mvn -version
> ```

1. Ir a [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)
2. Descargar el binario `apache-maven-3.x.x-bin.zip`.
3. Descomprimir en una carpeta fija, por ejemplo: `C:\tools\maven`.
4. Agregar `C:\tools\maven\bin` a la variable de entorno `Path`:
   - Buscar **"Variables de entorno"** en el menú inicio.
   - En **Variables del sistema**, editar `Path` y agregar la ruta.
5. Reiniciar la terminal y verificar:
   ```powershell
   mvn -version
   # Esperado: Apache Maven 3.x.x ...
   ```

---

## Levantar los servicios

> **Importante:** hay que respetar el orden de inicio. **Eureka Server debe estar corriendo antes que el resto**, ya que los demás servicios se registran en él al arrancar. Abrir una terminal por cada proyecto.

### 1 — eureka-server (puerto 8761) ⚠️ Primero

```powershell
cd eureka-server
.\mvnw.cmd spring-boot:run
```

Esperar hasta ver en la consola el mensaje:

```
Started EurekaServerApplication in X.XXX seconds
```

Luego verificar que la consola de Eureka esté disponible en: **http://localhost:8761**

---

### 2 — ms-producto (puerto 8081)

```powershell
cd ms-producto
.\mvnw.cmd spring-boot:run
```

### 3 — ms-pago (puerto 8082)

```powershell
cd ms-pago
.\mvnw.cmd spring-boot:run
```

### 4 — ms-pedido (puerto 8083)

```powershell
cd ms-pedido
.\mvnw.cmd spring-boot:run
```

### 5 — Gateway (puerto 8080)

```powershell
cd gateway
.\mvnw.cmd spring-boot:run
```

Una vez que los cinco estén corriendo, el punto de entrada principal es:

```
http://localhost:8080
```

Y en la consola de Eureka (`http://localhost:8761`) deberían aparecer registrados: **GATEWAY**, **MS-PRODUCTO**, **MS-PAGO** y **MS-PEDIDO**.

---

## Bases de datos H2

Cada microservicio tiene su propia base de datos en memoria, independiente de las demás. Los datos se crean al iniciar y se pierden al apagar cada servicio.

La consola web de H2 está habilitada en cada uno:

| Microservicio | Consola H2 | JDBC URL |
|---|---|---|
| ms-producto | http://localhost:8081/h2-console | `jdbc:h2:mem:productodb` |
| ms-pago | http://localhost:8082/h2-console | `jdbc:h2:mem:pagodb` |
| ms-pedido | http://localhost:8083/h2-console | `jdbc:h2:mem:pedidodb` |

En todos los casos:
- **Usuario:** `sa`
- **Contraseña:** *(vacía)*