## Ingeniería en sistemas computaciones

### Datos masivos

<p align="center">
    <img alt="Logo" src="https://www.tijuana.tecnm.mx/wp-content/themes/tecnm/images/logo_TECT.png" width=250 height=250>
</p>


**Profesor**<br>
Jose Christian Romero

## Unit 1 
### Exam 1

**Alumnos:** 

Marquez Millan Seashell Vanessa 

Galaviz Lona Oscar Eduardo


<br><br><br><br><br><br><br><br><br>
<br><br><br><br><br><br><br><br><br>




---
### Desarrollo

<br><br>

  **1-.SPARK session** <br>

Only start SPARK with the comand 
```scala 
SPARK-SHELL
```
  <p >
    <img alt="Evidence1" src="./../Media/Evidence_1.png" >
</p>

<br><br>

**2-. File Netflix Stock CSV** <br>

First need import the library, and is important you have the dataFrame in this address "/home/"name computer"/"name dataFrame" because the comand stearchh the archive here, then only print the data types
```scala 
import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema","true")csv("Netflix_2011_2016.csv")
df
df.printSchema()
```
  <p >
    <img alt="Evidence1" src="./../Media/Evidence_2.png" >
</p>

<br><br>

**3-.Names columns** <br>

Only start SPARK with the comand 
```scala 
df.columns
```
  <p >
    <img alt="Evidence1" src="./../Media/Evidencia_3.png" >
</p>

<br><br>

**4-.Schema** <br>

For know thw schema only need the dataFrame and the next reserverd word, is for can you know the structure and the types of each column
```scala 
df.printSchema()
```
  <p >
    <img alt="Evidence1" src="./../Media/Evidence_4.png" >
</p>

<br><br>

**5-Print first 5 columns** <br>

For that need create a variable inthis case cr and i say is equals to 0, then use while for create a bucle ever cr is less than 5, print the columns  and increase cr if i don't do that is a infinite bucle
```scala 
var cr = 0
while(cr < 5){
    println( df.columns(cr))
    cr=cr+1
}
```
  <p >
    <img alt="Evidence1" src="./../Media/Evidencia_5.png" >
</p>

<br><br>

**6-.Uses describe ()** <br>

That comand is for knows more informations about the dataFrame, statistical data
```scala 
df.describe().show()
```
  <p >
    <img alt="Evidence1" src="./../Media/Evidence_6.png" >
</p>

<br><br>

**7-.Create new DataFrame with new column** <br>

We need create a new dataFrame for can to do some modification so here to make a new column with the relationship of column High and Volume
```scala 
val newData = df.withColumn("HVRatio", df("High")/df("Volume"))
newData.show()
```
  <p >
    <img alt="Evidence1" src="./../Media/Evidence_7.png" >
</p>

<br><br>

**8-.Max Open** <br>

We need know the date of the maxium data, so first we order the column Open and save in maxp then select Date of de maxp but only the first row
```scala 
val maxp = newData.orderBy(desc("Open"))
maxp.select("Date").limit(1).show()
```
  <p >
    <img alt="Evidence1" src="./../Media/Evidence_8.png" >
</p>

<br><br>

**9-.Meaning Close in DataFrame** <br>


```scala 
newData.orderBy(desc("Close")).show()
```
 

<br><br>

**10-.Maximum and minimum of Volume** <br>

This is only to know thw first row the most big and the most lowest, and oly select the volume and your minium or maximun
```scala 
df.select(max("Volume")).show()
df.select(min("Volume")).show()
```
  <p >
    <img alt="Evidence1" src="./../Media/Evidence_9.png" >
</p>

<br><br>

**11-.With Scala/Spark $ resolve the next** <br><br>

**A-.With Scala/Spark $ resolve the next** <br>
Need to know the data less than numbesr 600 and cout that 
```scala 
df.filter($"Close"<600).count()
```
  <p >
    <img alt="Evidence1" src="./../Media/Evidence_11a.png" >
</p>