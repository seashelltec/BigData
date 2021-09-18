//Cyrcle

 var D: Double= 10.5
 var Pi: Double =3.14592654
 (D * Pi)/2

//Pair number

var num: Int= 10
if(num %2==0){
println("The number is pair")
}else{
    println("The number is odd")
}

//Add string
val name = "tweet"
val phrase = "Estoy escribiendo un "
val complite = s"$phrase $name"
complite

//Remove word of string

var mensaje = "Hola Luke yo soy tu padre"

//Only Luke

var name = mensaje slice  (5,9)

//Only "Hola yo soy tu padre"
var first = mensaje slice  (0,4)
var second = mensaje slice  (9,25)
s"$first $second"

//Tuple

val tuple = (2,4,5,1,2,3,3.1416,23)
tuple._7