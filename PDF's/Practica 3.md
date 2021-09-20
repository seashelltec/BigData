## Ingeniería en sistemas computaciones

### Datos masivos

<p align="center">
    <img alt="Logo" src="https://www.tijuana.tecnm.mx/wp-content/themes/tecnm/images/logo_TECT.png" width=250 height=250>
</p>



### Practica 2

**Alumnos:** 

Marquez Millan Seashell Vanessa 

Galaviz Lona Oscar Eduardo






---


***

``` scala
//1-. lista 

val lista = List("Rojo","Blanco","Negro")

//2-. lista nueva

val lista = List("Rojo","Blanco","Negro","Amarillo","Azul","Verde","Naranja","Perla")
lista :+ ("Amarillo"):+("Azul"):+("Verde"):+("Naranja"):+("Perla")
//3-. Traer elementos

lista(4)
lista(5)
lista(6)

//4-. arreglo

Array.range(1,1000, 5)

//5-. elementos unicos

val lista5 = Set(1,3,3,4,6,7,3,7)

//6-. mapa mutable
val nombre = collection.mutable.Map(("Jose",20)("Luis",24)("Ana",23)("Susana",27))

//6a-. llaves
nombre.keys
//6b-. valor nuevo
nombre += ("Miguel" -> 23)

```
