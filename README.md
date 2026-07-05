# Prueba Técnica — Backend Java / Spring Boot

API REST de gestión de ventas: clientes, artículos, ventas con JWT - Spring Boot, MySQL

## Levantar el proyecto

Opción 1: Local con MySQL

1. Modificar usuario & contraseña en `src/main/resources/application.properties` si es distinto a `root/admin`.
2. Ejecutar el comando:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
3. API disponible en `http://localhost:8080`.

Opción 2: Docker Compose

1. Ejecutar el comando:
   ```bash
   docker-compose up --build
   ```

Levantará la API y MySQL en uno y la API quedará disponible en `http://localhost:8080`.

## Usuarios semilla

La tabla de usuarios se crea automáticamente al levantar la API, con los siguientes datos:

```
  - username: `chabelo`
  - password: `pepe`
```

## Tests

Para ejecutar los tests, se puede usar el comando:

```bash
mvn test
```

Se desarrollaron los siguientes tests:

- Cálculo del total de una venta a partir de las líneas de detalle.
- Validación y descuento de stock al registrar una venta.
- Reposición de stock y cambio de estado al cancelar una venta.
- Validación de código de artículo duplicado.

Este usuario se usa en `POST /api/auth/login` para obtener el token JWT.

# Endpoints disponibles

Se adjunta el archivo `pm_postman_collection.json` para importar en Postman y probar los endpoints.

# Decisiones de diseño

- Decidí utilizar Java 17 + SpringBoot 4 ya que es una versión más reciente y estable, lo que permite aprovechar
  las últimas características del lenguaje y del framework.
- Decidí que la baja lógica sería la mejor opción y dejar un campo `activo` en lugar de eliminar la entidad.
  Esto por que si un cliente o artículo se decide "eliminar" se podrá seguir interactuando y hacer referencia
  a ellos, de manera que puede haber un historial para ambas entidades, usar la opción de primeras creo que
  podría llevar más trabajo y afectar la estructura de la base de datos.
- El total de la venta se calculará en el servidor, el cliente solo envía `clienteId` y las líneas de detalle
  (artículo + cantidad); el precio unitario y el total se toman siempre del artículo en base de datos,
  nunca de lo que envíe el request. De estar manera, el cliente no puede cambiar el precio unitario ni el
  total de la venta y sé evita la manipulación de datos.
- Decidí usar una tabla mínima `usuario` con un registro semilla que será generado al iniciar, de esta manera
  me pude enfocar en otras funciones.

# ¿Qué haría si tuviera más tiempo?

- Si me hubiera gustado ver algo de los tests en Controllers, he de admitir que no había hecho pruebas tan
  directas y al punto de buscar y entender como se hacían con las unitarias me dió curiosidad.
- Me hubiera gustado implementar un frontend, un tema más de visibilidad, lo inicié, pero no lo he terminado.