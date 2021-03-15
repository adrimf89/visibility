# Algoritmo de visibilidad

### Ejecución

Con Maven (versión 3.6) i Java (versión 8) instalados:

    mvn spring-boot:run

### Estructuras de datos utilizadas en el algoritmo

* Product: TreeSet. Dado que permite almacenar los productos ordenados por sequence una vez insertados.

* Size: HashMap. Permite tener las tallas agrupadas por producto y permite acceder a ellas más fácilmente.

* Stock: HashMap. Permite tener el stock agrupado por talla y permite acceder a el más fácilmente.

### Algoritmo de visibilidad

El algoritmo tiene una complejidad de O(n^2) dado que primero recorremos el listado de productos y por cada producto recorremos el listado de tallas (O(n) + O(n)).
Con el modelo de datos actual es difícil mejorar el tiempo, ya que debemos recorrer todos los productos y tallas para poder mostrar un resultado.
     