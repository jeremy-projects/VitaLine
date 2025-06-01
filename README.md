# VitaLine
Este programa permite gestionar una base de datos de donantes de sangre organizados por ciudad. Utiliza un árbol binario de búsqueda (BST) para almacenar ciudades, y en cada ciudad, mantiene una lista enlazada de donantes. Los datos pueden ser agregados, consultados, ordenados y modificados a través de una interfaz gráfica sencilla con JOptionPane.

# ¿Cómo está estructurado el código?
El código está compuesto por cuatro clases principales:
1. **DonanTe:** representa a una persona donante.
2. **NodoCiudad:** representa un nodo del árbol, con una ciudad y su lista de
donantes.
3. **ArbolCiudades:** gestiona el árbol binario y la lógica de los donantes.
4. **Código principal (main):** contiene el menú con las opciones que el usuario
puede elegir para interactuar con el sistema.

# Flujo general del programa:
- Se inicia el programa y aparece un menú con opciones como agregar, eliminar, buscar, actualizar y ordenar donantes.
- El usuario elige una opción.
- El programa ejecuta la lógica correspondiente usando métodos de 
ArbolCiudades.

# ¿Cuál es la finalidad o el propósito principal del programa?
El objetivo es llevar un registro ordenado y accesible de donantes de sangre por ciudad. Permite:
Agregar donantes.
- Ver donantes por ciudad.
- Buscar por tipo de sangre.
- Ordenar por fecha de última donación.
- Actualizar fechas.
- Eliminar donantes.

# ¿Para qué sirve cada clase?
1. DonanTe
- Representa a un donante individual.
- Contiene: id, nombre, tipoSangre, fechaUltimaDonacion y un puntero siguiente (para formar una lista enlazada).

2. NodoCiudad
- Nodo del árbol binario de búsqueda.
- Representa una ciudad.
- Contiene: nombreCiudad, punteros a izquierda y derecha, y una cabeza de lista enlazada de donantes.

3. ArbolCiudades
- Clase más importante.
- Implementa un árbol binario para organizar las ciudades alfabéticamente.
- Gestiona toda la lógica del programa: agregar, eliminar, buscar, ordenar y
actualizar donantes.