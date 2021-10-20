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





**5-. ¿Cúal es la diferencia entre value (val) y una variable (var) en scala?**

Respuesta: var se puede reasignar y val no ya que es fijo.

***

**Codigo**

``` scala

//1-. Cyrcle

 var D: Double= 10.5
 var Pi: Double =3.14592654
 (D * Pi)/2

//2-.Pair number

var num: Int= 10
if(num %2==0){
println("The number is pair")
}else{
    println("The number is odd")
}

//3-.Add string
val name = "tweet"
val phrase = "Estoy escribiendo un "
val complite = s"$phrase $name"
complite

//4-.Remove word of string

var mensaje = "Hola Luke yo soy tu padre"

//Only Luke

var name = mensaje slice  (5,9)

//Only "Hola yo soy tu padre"
var first = mensaje slice  (0,4)
var second = mensaje slice  (9,25)
s"$first $second"

//6-.Tuple

val tuple = (2,4,5,1,2,3,3.1416,23)
tuple._7
 
 ```






