//Practica 4

// Practica 4


//1-.

def Serie1(num:Int): Int={
if(num<2){
    return num
}else{
    return Serie1(num-1)+Serie1(num-2)

}
    }

println(Serie1(10))

//2-.

import scala.math.sqrt
import scala.math.pow
def Serie2(num: Double):Double ={
    var aux = 0.0
    if(num<2){
        return num
    }else{
        val aux2 = (1+sqrt(5))/2
        aux=pow(aux2,num)
        aux= aux-pow((1.0-aux2),num)
        aux = aux /sqrt(5)
    }
    return aux
}
println(Serie2(8))

//3-.

def Serie3(num: Int): Int={
    var a =0
    var b =0
    var c=1
    for(k<- 0 until num){
        b=c+a
        a=c
        c=b
    }
    return a
}

println(Serie3(8))