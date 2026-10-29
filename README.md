# Dojo: records and forms

[Español](README.es.md) · [More work](https://github.com/navalcode)

## Overview

A revised DAM coursework application for students, courses and instructors, with server-rendered Thymeleaf pages and **28 local tests**: the original context, 23 MVC/JPA journeys and four graph contracts. This remains a learning project, with its original attribution and toolchain.

The interface uses local Outfit, paper/ink/copper colors, open roster rows and labeled forms. Spanish is the default, with complete English resources. Mobile filters use a native disclosure; without JavaScript, filters and forms remain available. The interface supports combined course filtering and age/name order, student detail, and creating/editing all three entity types.

<img src="docs/images/students-en-desktop.png" alt="Actual English Dojo directory, six fictional students, captured from the local packaged application" width="800">

## Run

JDK 17 was used for verification; the POM still targets Java 11. From the repository root:

```sh
cd DojoNavalManuel
sh ./mvnw verify
java -jar target/dojonavalmanuel-0.0.1-SNAPSHOT.jar
```

Tomcat prints the assigned port. Open that loopback URL in a browser. The default H2 database is memory-only and resets when the process stops. The demo seeds six fictional students, two courses and two instructors; contacts use example.invalid and no photos are loaded initially.

File persistence is an explicit opt-in. From the same module directory:

```sh
mkdir -p demo-data
java -jar target/dojonavalmanuel-0.0.1-SNAPSHOT.jar '--spring.datasource.url=jdbc:h2:file:./demo-data/dojo;DB_CLOSE_ON_EXIT=FALSE'
```

An isolated create/stop/restart check retained an added record and did not duplicate the seed. The original user database path is never opened by default. `DOJO_DATABASE_URL` and `DOJO_PORT` are optional configuration alternatives.

## Behavior worth reviewing

| Action | Result |
|---|---|
| Create/edit from the visible forms | Validates before writing; errors preserve entered values |
| Change language while editing | Posts the draft for translation without saving it |
| GET a deletion URL | Read-only confirmation; cancel preserves the record |
| Confirm deletion with POST | Requires the form token and session; removes only an eligible record |
| Delete a course/instructor still referenced | 409 with an explanation and a route back |
| Open a missing record | 404 recovery page |
| Missing/invalid form token | 403 recovery page, without a second rejection of the error dispatch |

New input models use English field names rather than binding entities directly. Historical navigation URLs remain; old programmatic form submissions need the new fields and token. Optional contact fields are not required. An explicitly entered image URL can load a remote image; no external photo/icon/font service is part of the seeded experience.

Read [the MVC/JPA journeys](DojoNavalManuel/src/test/java/com/salesianostriana/dam/DojoHttpJourneyTest.java), then [DojoService](DojoNavalManuel/src/main/java/com/salesianostriana/dam/web/DojoService.java). Tests reload repositories after requests without an enclosing test transaction. Graph contracts cover ownership transfer, repeated attachment and wrong-owner removal. Course/instructor deletion retains referential integrity; monetary input is bounded, but the historical model still stores double values.

<img src="docs/images/students-es-mobile.png" alt="Actual Spanish directory on a 360 pixel viewport, with collapsed filters and a complete student row" width="260"> <img src="docs/images/form-es-mobile-error.png" alt="Actual Spanish validation error on a mobile form, retaining the fictional surname Demo" width="260">

## Verification and limits

Maven verify passed 28 tests with no failures/errors/skips on JDK 17/macOS. A packaged JAR was also checked over actual HTTP, including both languages, routes, local asset bytes, confirmation and styled errors; optional file persistence was checked across restart. Browser review covered 1366×768, 360×800 and 800×360, keyboard skip link, create/reload/edit, draft language change and combined filters. Captures are real application pixels, not generated concepts. [Recorded results](docs/verification.json).

No new hosted CI was added. Spring Boot 2.4.5 and the existing Maven wrapper remain pinned; this is not an authenticated production management system. Formal screen-reader/WCAG auditing, physical devices and deployment are not claimed. [Design decisions](DESIGN.md) · [Components and attribution](THIRD_PARTY.md).

## Resumen

[La versión española completa](README.es.md) describe los mismos contratos, comandos y límites.

## Original notes

Historical notes and credits are retained verbatim below; the reviewed behavior and commands above take precedence.

<details>
<summary>Original documentation and credits · Documentación y créditos originales</summary>

# Dojo Sakura

_Proyecto final 1º DAM _

_Repositorio público desde 28/09/2021_

## Comenzando 📋

_La idea principal de este proyecto era proponer una solución integral a la gestión de un dojo de karate, el núcleo principal de un dojo son los alumnos,
pero además se gestiónan actividades como excursiones, exhibiciones, exámenes, convivencias, etc. Finalmente, debido a las limitaciones de tiempo, se ha reducido
el proyecto a sus funciones básicas, concretamente se manejan 3 entidades, Alumno, Curso y Sensei. Las funciones disponibles para todos ellos son Agregar,
Editar, Eliminar. Para Alumno se añaden además las funciones de filtrar por curso y la ordenación alfabéticamente y por fecha de nacimiento, junto con la posibilidad
de ver todos los detalles sin necesidad de entrar en el formulario de edición._

### Futuras mejoras 💡

_Sin tener en cuenta la ampliación de entidades hay una serie de mejoras suculentas que podríamos aplicar al proyecto_

###### Asignación de cursos automática por edad.

_En un dojo los cursos no siguen un ritmo académico, se rigen por la edad de los alumnos, de este modo un alumno es juvenil desde los 5 años hasta los 8, un alevin desde los 9 a los 14, y a partir
de los 15 años todos son considerados adultos (principalmente porque es más sencillos emparejar a los alumnos por peso y altura de este modo.
Esta asignación debería ser automatica en el futuro dependiendo de la edad, ahora mismo se puede realizar solo bajo edición con el formulario._

###### Ascenso a Sensei.

_Aquella frase de alumno es el maestro ahora, suele ser muy común en karate, llegados a cierto grados los alumnos pueden ser Sensei, esta conversión debería poder hacerse 
sin perder los datos del alumno._

###### Lógica de negocio.

_Tanto la gestión de la ecónomia, mensualidades, gastos de electricidad, agua, fungibles, la gestion de materiales como armas de prácticas, protecciones, cinturones... Y un 
larguísimo etcetera de las necesidades que engloban un dojo se deberían acabar introduciendo en un futuro para que la aplicación fuera realmente útil._

###### Aspecto visual.

_Si bien la interfaz de usuario está pensada para tratar de ser lo más simple e intuitiva es posible que el aspecto gráfico no este del todo refinado, se podrían redimensionar
algunos botonoes e iconos, gestionar mejor el tamaño de la plantilla que muestra la información detallada del alumno, así como facilitar su lectura._

## Pruebas ⚙️

_Se han llevado a cabo pruebas manuales en la introducción de datos de los distintos formularios así como la intreacción con lso botones agregar, editar y eliminar.
Para ver más detalles sobre las pruebas puedes ver el documento PruebasDojo.pdf disponible en este mismo repositorio._


## Autor ✒️



* **Manuel Naval** - *Proyecto Dojo Sakura* - [navalcode](https://github.com/navalcode)

</details>
