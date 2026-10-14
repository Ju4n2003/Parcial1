# QuindioExpress - Sistema de Gestión de Paquetes

## 1. Descripción del proyecto

QuindioExpress es una aplicación desarrollada en Java que permite gestionar las operaciones básicas de un centro de distribución de paquetes. El sistema permite registrar paquetes y repartidores, consultar información, organizar los despachos, asignar paquetes a repartidores y registrar las entregas realizadas.

El proyecto tiene como objetivo aplicar los conceptos de estructuras de datos, algoritmos de búsqueda, recursividad, divide y vencerás y análisis de complejidad para mejorar la organización y consulta de la información.

## 2. Objetivos

### Objetivo general

Desarrollar un sistema de gestión de paquetes que utilice diferentes estructuras de datos y algoritmos para organizar, consultar y controlar los procesos de distribución y entrega.

### Objetivos específicos

- Implementar el registro y la consulta de paquetes y repartidores.
- Utilizar estructuras de datos para organizar la información según las necesidades del sistema.
- Aplicar algoritmos de búsqueda para localizar paquetes por su código.
- Implementar algoritmos recursivos para calcular pesos y contar paquetes según su prioridad.
- Utilizar el enfoque de divide y vencerás para encontrar el paquete de mayor peso.
- Comparar el rendimiento de diferentes estructuras de datos al realizar búsquedas.

## 3. Funcionalidades principales

El sistema cuenta con un menú interactivo por consola que permite realizar las siguientes operaciones:

1. Consultar el resumen del centro de distribución.
2. Mostrar los destinos registrados en orden alfabético.
3. Buscar paquetes por código mediante búsqueda secuencial.
4. Buscar paquetes por código mediante búsqueda binaria.
5. Calcular el peso total de los paquetes de un destino.
6. Contar los paquetes que cumplen con una prioridad mínima.
7. Encontrar el paquete de mayor peso.
8. Consultar el siguiente paquete pendiente de despacho.
9. Asignar paquetes a repartidores.
10. Registrar la entrega de paquetes.
11. Registrar nuevos paquetes.
12. Salir del programa.

Al iniciar, el sistema registra automáticamente 30 paquetes y 6 repartidores de ejemplo para facilitar las pruebas.

## 4. Estructuras de datos utilizadas

| Estructura | Uso en el proyecto |
|---|---|
| `ArrayList` | Almacenar los paquetes registrados y las listas de paquetes por destino. |
| `HashMap` | Consultar rápidamente paquetes por código y repartidores por identificación. También permite organizar paquetes por destino. |
| `HashSet` | Almacenar destinos únicos sin necesidad de mantenerlos ordenados. |
| `TreeSet` | Mantener los destinos ordenados alfabéticamente. |
| `PriorityQueue` | Organizar los paquetes pendientes según su prioridad de despacho. |
| `ListaEnlazada` | Registrar el historial de entregas mediante una lista enlazada implementada en el proyecto. |

El uso de estas estructuras permite mantener la información organizada y utilizar diferentes alternativas según las operaciones que necesita realizar el sistema.

## 5. Algoritmos implementados

### 5.1. Búsqueda secuencial

Recorre los paquetes uno por uno hasta encontrar el código solicitado o llegar al final de la lista.

- Complejidad temporal: `O(n)` en el peor caso.
- Se utiliza para realizar consultas sobre la lista de paquetes.

### 5.2. Búsqueda binaria

Busca un paquete dividiendo repetidamente el intervalo de búsqueda en dos partes. Para funcionar correctamente, la lista debe estar ordenada por código de forma ascendente.

- Complejidad temporal de la búsqueda: `O(log n)`.
- En el sistema, primero se copia y ordena la lista, por lo que la operación completa también incluye el costo de ordenamiento, aproximadamente `O(n log n)`.

### 5.3. Cálculo recursivo del peso por destino

Recorre recursivamente la lista de paquetes y suma el peso de aquellos cuyo destino coincide con el solicitado.

- Complejidad temporal: `O(n)`.
- Complejidad espacial auxiliar: `O(n)`, debido a las llamadas recursivas.

### 5.4. Conteo recursivo por prioridad

Cuenta recursivamente los paquetes cuya prioridad es mayor o igual al valor mínimo indicado.

- Complejidad temporal: `O(n)`.
- Complejidad espacial auxiliar: `O(n)`, por la profundidad de la recursión.

La prioridad debe estar comprendida entre 1 y 5.

### 5.5. Paquete de mayor peso mediante divide y vencerás

Divide la lista en dos partes, encuentra el paquete más pesado de cada una y compara los resultados para obtener el mayor de todos.

- Complejidad temporal: `O(n)`.
- Complejidad espacial auxiliar: `O(log n)` para una división equilibrada, debido a la profundidad de la recursión.

### 5.6. Comparación entre ArrayList y HashMap

La clase `ComparacionBusquedaPaquetes` permite comparar el tiempo de búsqueda de un paquete mediante una búsqueda secuencial en un `ArrayList` y una consulta por clave en un `HashMap`.

| Estructura | Complejidad temporal esperada de búsqueda |
|---|---|
| `ArrayList` con búsqueda secuencial | `O(n)` |
| `HashMap` mediante `get(clave)` | `O(1)` en promedio |

La clase realiza 100 consultas repetidas y prueba con 30 y 500.000 paquetes. Los tiempos obtenidos pueden variar según el equipo, la ejecución de la máquina virtual de Java y las condiciones de la prueba.

## 6. Modelo de clases

El proyecto está organizado en paquetes para separar las responsabilidades de cada componente.

- **`model`**: contiene las clases `Paquete`, `Repartidor` y el enumerado `EstadoPaquete`.
- **`service`**: contiene `CentroDistribucion`, encargado de administrar los registros y las operaciones del sistema.
- **`structure`**: contiene la interfaz genérica `Coleccion` y la implementación propia `ListaEnlazada`.
- **`algorithm`**: contiene `AnalisisPaquetes`, donde se implementan los algoritmos de búsqueda, recursividad y divide y vencerás.
- **`comparator`**: contiene `ComparadoresPaquete` y `ComparacionBusquedaPaquetes`, encargadas de definir criterios de ordenamiento y comparar el rendimiento de las búsquedas.
- **`app`**: contiene `Main`, que presenta el menú y permite interactuar con el usuario.

## 7. Reglas de funcionamiento

El sistema valida diferentes condiciones para mantener la consistencia de la información:

- Los códigos de los paquetes y las identificaciones de los repartidores no pueden estar duplicados.
- El peso y el tiempo estimado de un paquete deben ser mayores que cero.
- La prioridad debe estar entre 1 y 5.
- Solo se pueden asignar paquetes que estén pendientes.
- El repartidor debe estar disponible y su zona debe coincidir con el destino del paquete.
- Solo se pueden registrar entregas de paquetes previamente asignados.
- Cuando se registra una entrega, el paquete cambia al estado `ENTREGADO` y el repartidor vuelve a estar disponible.

Los estados posibles de un paquete son `PENDIENTE`, `ASIGNADO` y `ENTREGADO`.

## 8. Requisitos para ejecutar el proyecto

- Tener instalado el **JDK de Java**.
- Contar con un IDE compatible con Java, como Eclipse o IntelliJ IDEA, o utilizar la terminal.
- Tener configurado el proyecto para reconocer los paquetes y las clases de la aplicación.

## 9. Ejecución

1. Descargar o clonar el repositorio.
2. Abrir el proyecto en el IDE de preferencia.
3. Verificar la configuración del JDK.
4. Ejecutar la clase `Main.java`, ubicada en `com.quindioexpress.app`.
5. Utilizar el menú de la consola para seleccionar las operaciones disponibles.

Para ejecutar la comparación de rendimiento entre `ArrayList` y `HashMap`, se puede ejecutar por separado la clase `ComparacionBusquedaPaquetes.java`.

## 10. Conclusiones

El desarrollo de QuindioExpress permite aplicar diferentes estructuras de datos y algoritmos a un problema práctico de gestión de paquetes. Se evidencia que cada estructura tiene características particulares y que su elección depende de la operación que se desea realizar.

El uso de `HashMap` facilita las búsquedas por código, mientras que `TreeSet` permite mantener los destinos ordenados y `PriorityQueue` organiza los paquetes pendientes según el criterio de prioridad establecido. Asimismo, los algoritmos recursivos y la técnica de divide y vencerás permiten resolver problemas de cálculo y comparación de manera estructurada.

Finalmente, la comparación entre `ArrayList` y `HashMap` permite analizar cómo la elección de una estructura de datos puede influir en el rendimiento de una aplicación, especialmente cuando aumenta la cantidad de información.

## 11. Tecnologías utilizadas

- **Lenguaje de programación:** Java.
- **Estructuras de datos:** listas dinámicas, mapas, conjuntos, colas de prioridad y lista enlazada propia.
- **Conceptos algorítmicos:** búsqueda secuencial, búsqueda binaria, recursividad, divide y vencerás y análisis de complejidad.
- **Interfaz:** menú interactivo por consola.

---

**Proyecto académico:** Estructura de Datos  
**Nombre del sistema:** QuindioExpress  
**Autores**: María José Valencia - Nykol Xiomara Ramírez - Juan Esteban Rodriguez
