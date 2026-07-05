Prueba Técnica — Backend Java / Spring Boot
Nivel: Junior · Modalidad: proyecto a resolver en casa
1. Objetivo
   Evaluar tu capacidad para diseñar y construir una API REST sencilla pero bien estructurada, usando Java y Spring
   Boot, aplicando buenas prácticas de modelado de datos, arquitectura por capas y testing. No buscamos un sistema
   perfecto ni con todas las funcionalidades posibles: nos interesa ver cómo piensas, cómo organizas el código y qué
   decisiones tomas.
2. Descripción general
   Tienes que construir el backend de un sistema simple de ventas. El sistema debe permitir administrar clientes y
   artículos, y registrar ventas que asocian un cliente con uno o más artículos, cada uno con su cantidad.
3. Requisitos funcionales
   3.1 Clientes ✅
    Alta, baja (lógica o física), modificación y consulta de clientes. ✅
    Consulta de un cliente por id y listado general con paginación (obligatoria, ver sección 4). ✅
   3.2 Artículos ✅
    Alta, baja, modificación y consulta de artículos. ✅
    Cada artículo tiene un precio y un stock disponible. ✅
    El código del artículo es único: no se puede repetir entre artículos. ✅
    Listado general de artículos con paginación (obligatoria). ✅
   3.3 Ventas ✅
    Registrar una venta asociada a un cliente existente, con una o más líneas de detalle (artículo + cantidad). ✅
    Cada venta debe generar un folio incremental, único, que no se pueda repetir. ✅
    Toda venta tiene un estado: activo o cancelado. ✅
    Al registrar la venta, se debe validar el stock disponible y descontarlo. ✅
    El total de la venta se calcula en el servidor a partir de las líneas de detalle (no confiar en un total enviado por
   el cliente). ✅
    Listar ventas (con paginación obligatoria) y consultar el detalle de una venta puntual. ✅
    Permitir cancelar una venta, cambiando su estado a &quot;cancelado&quot; (y, si lo implementas, reponer el stock
   correspondiente). ✅
   Nota: el modelo de datos que sigue es una sugerencia de punto de partida. Puedes ajustarlo si tu diseño lo
   justifica; lo importante es que quede documentado el por qué.

Entidad Campos sugeridos Notas ✅
Cliente id, nombre, email, teléfono, dirección Validar formato de email. ✅
Artículo id, código, nombre, descripción, precio,✅

stock

El precio y el stock no pueden ser negativos. El
código no se puede repetir (único). ✅

Venta id, folio, fecha, cliente, estado, total, detalle✅

(líneas)

El folio es incremental y no se puede repetir
(único). Estado: activo o cancelado. El total se
calcula a partir de las líneas de detalle.✅

Detalle de Venta id, venta, artículo, cantidad, precio unitario,✅

subtotal

Una venta tiene una o más líneas de detalle.✅

3.4 Autenticación
 La API debe estar protegida con autenticación basada en JWT. ✅
 Debe existir un endpoint de login (usuario y contraseña) que, si las credenciales son válidas, devuelva un
token JWT. ✅
 Los endpoints de clientes, artículos y ventas solo deben ser accesibles con un token válido en el header de la
petición. ✅
 El usuario para autenticarse puede ser simple (por ejemplo, un usuario fijo o una tabla mínima de usuarios);
no hace falta un CRUD de usuarios. ✅
4. Requisitos técnicos
    Lenguaje: Java 8. ✅
    Framework: Spring Boot (Web, Data JPA, Validation). ✅
    API: REST, con respuestas en JSON y códigos HTTP apropiados. ✅
    Persistencia: MySQL de forma obligatoria. No se acepta otro motor de base de datos (ni H2, ni PostgreSQL,
   etc.). ✅
    Build: Maven. ✅
    Control de versiones: Git, con commits que reflejen el avance (evitar un único commit final). ✅
    Paginación: obligatoria en todos los listados (clientes, artículos y ventas). ✅
    Seguridad: Spring Security + JWT para autenticar y proteger los endpoints de la API. ✅
5. Arquitectura y buenas prácticas esperadas ✅
    Separación clara en capas: controller / service / repository (y DTOs para no exponer las entidades
   directamente en la API). ✅
    Validaciones de entrada (Bean Validation) y manejo centralizado de errores, con respuestas de error
   consistentes. ✅
    Nombres y estructura de paquetes claros; código legible por sobre código &quot;ingenioso&quot;. ✅
    Manejo apropiado de transacciones donde corresponda (por ejemplo, al registrar una venta y descontar stock). ✅
6. Testing✅

 Pruebas unitarias con JUnit (+ Mockito) sobre la lógica de negocio, en particular el cálculo de totales y la
validación de stock. ✅
 Las pruebas de integración sobre los controllers son un plus, no obligatorias para este nivel. ✅
7. Entregables
    Repositorio Git público (GitHub, GitLab o Bitbucket) o un .zip del proyecto. ✅
    README con: instrucciones para levantar el proyecto y correr los tests, decisiones de diseño relevantes, y
   qué harías distinto si tuvieras más tiempo. ⏳
    Opcional: colección de Postman o archivo .http con ejemplos de uso de los endpoints. ✅
8. Extras opcionales (suman puntos, no son obligatorios)
    Documentación de la API con Swagger / OpenAPI. ✅
    Filtros adicionales en los listados (por ejemplo, ventas por cliente o por rango de fechas). ⏳
    Dockerfile o docker-compose para levantar la app y la base de datos. ⏳
    Manejo de una entidad adicional simple si aporta al modelo (por ejemplo, categoría de artículo). ⏳
9. Modalidad de entrega
   Los resultados deben enviarse al correo: fabricio.soto@macronnect.com.mx
   Fecha límite de entrega: lunes 6 de julio de 2026, 9:00 a.m.
10. Criterios de evaluación
    Criterio Qué se evalúa
    Funcionalidad Que el CRUD de clientes, artículos y ventas funcione end-to-end, cumpla las
    reglas de negocio y que la autenticación con JWT proteja correctamente los
    endpoints.

Diseño de API REST Uso correcto de verbos y códigos HTTP, rutas coherentes, formato de

request/response, manejo de errores. ✅

Modelado de datos Entidades y relaciones bien definidas (Cliente, Artículo, Venta, DetalleVenta), 

integridad y consistencia. ✅

Arquitectura y buenas prácticas Separación en capas, uso de DTOs, legibilidad, principios SOLID, manejo

centralizado de excepciones. ✅

Testing Cobertura razonable de pruebas unitarias, especialmente en la capa de

servicio/reglas de negocio.

11. Consideraciones finales
     Puedes usar cualquier librería estándar del ecosistema Spring; evita dependencias innecesarias o &quot;mágicas&quot;
    que oculten la lógica que queremos ver.
     Si usas asistentes de IA como apoyo, está bien, pero tienes que poder explicar y defender cada decisión del
    código en la entrevista técnica posterior.

 Ante cualquier duda sobre el alcance, toma la decisión que te parezca más razonable y documéntala en el
README. Evaluamos también tu criterio.