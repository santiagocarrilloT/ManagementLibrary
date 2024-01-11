# Manejo de libreria

## Programa realizado en Java.
Este es un programa que permite manejar un inventario de una biblioteca y utiliza serialización de datos. Este programa posee las siguientes funciones:
- Agregar, guardar y eliminar nuevos bibliotecarios.
- Agregar, guardar y eliminar libros y géneros.
- Agregar, guardar y eliminar a una lista personas las cuales pidieron el préstamos de un libro.
Además, luego de 7 días de prestado un libro, se cobrará un valor por multa que cada día pasado aumentará su valor.

Recomendaciones e información del programa:
- El programa se ejecuta desde la clase _IniciarSesion_ ubicada en el paquete **PaqueteBibliotecario**. El nombre de usuario es **santi** y la contraseña es **123**.
- Si no se puede iniciar sesión debido al usuario o contraseña. Ir a **PaqueteBibliotecario**, ejecutar el programa desde la clase _Registro_ y crear una nueva cuenta para lograr ejecutar el programa. 
- El programa posee un conjunto de libros y géneros predeterminados (tres géneros y cinco libros por cada género).
- Para volver a los géneros y libros predeterminados ir a **PaqueteLibroGenero** y seleccionar la clase _AggGL_, para reiniciar los géneros descomentar desde la línea 298 hasta 303, para los libros desde la misma clase descomentar desde la línea 335 hasta 374 y por último ejecutar el programa desde esta misma clase.
