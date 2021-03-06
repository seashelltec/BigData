<p align="center">
    <img alt="Logo" src="https://www.tijuana.tecnm.mx/wp-content/uploads/2021/08/liston-de-logos-oficiales-educacion-tecnm-FEB-2021.jpg" width=850 height=250>
</p>

<H2><p align="Center">TECNOLÓGICO NACIONAL DE MÉXICO</p></H2>

<H2><p align="Center">INSTITUTO TECNOLÓGICO DE TIJUANA</p></H2>

<H2><p align="Center">SUBDIRECCIÓN ACADÉMICA</p></H2>

<H2><p align="Center">DEPARTAMENTO DE SISTEMAS Y COMPUTACIÓN</p></H2>

<H2><p align="Center">NOMBRE DE LOS ALUMNOS: </p></H2>

<H2><p align="Center">GALAVIZ LONA OSCAR EDUARDO (N.CONTROL: 17212993)</p></H2>

<H2><p align="Center">MARQUEZ MILLAN SEASHELL VANESSA (N.CONTROL: ) </p></H2>

<H2><p align="Center">Carrera: Ingeniería Informática</p></H2>

<H2><p align="Center">Semestre: 9no </p></H2>

<H2><p align="Center">MATERIA: Datos Masivos</p></H2>

<H2><p align="Center">PROFESOR: JOSE CHRISTIAN ROMERO HERNANDEZ</p></H2>

<H2><p align="Center">Practica evaluatoria 1</p></H2>

<H2><p align="Center">Unidad 1</p></H2>

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

### Introduccion
En este documento se explicara la realizacion de la practica evaluatoria correspondiente a la unidad 1, veremos la utilizacion de una sesion de spark, la utilizacion de un archivo csv y la manipulacion de los datos dentro del mismo. pero antes de adentrarnos en la explicacion primero debemos entender que es Apache spark la herramienta que nos permitio realizar esta practica

Apache spark combina un sistema de computacion distribuida a traves de clusters de ordenadores mediante una manera sencilla y elegante de escribir programas, es considerado el primer software de codigo abierto permitiendo que la programacion distribuida sea accessible para los cientificos de datos 

En cuanto a lo que se refiere la herramienta apache spark es util y eficiente para tareas de procesamiento masivo de datos. El entender el lengua es facil ya que tiene1 la bases de otros lenguajes para realizacion de operaciones y manipulacion de datos, ademas cuenta con la importacion de funciones que no se tengan dentro del scala ademas de la utilizacion de los dataframes que son archivos o contenedores de datos que nos permitira ver, leer, manipular los datos de un archivo csv, esto nos permitara realizar operaciones que nos den informacion para sacar conclusiones respecto a los datos que obtenemos.

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

Here only we want see the column names, but we want see all columns on the DataFrame.
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
    <img alt="Evidence4" src="./../Media/Evidence_4.png" >
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
When the price of the High column goes up it seems to be the same for the Close column only that it is
always less than High, which means that as High
it goes up, the most probable thing is that Close will also do it but in less quantity


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

**B-.We need to know what is the percentage of time in this question**<br>
```scala

val tiempo:Double = df.filter($"High">500).count()
val porcentaje:Double = (tiempo*100)/1259
```
<p>
  <img alt="Evidence1" src="./../Media/Evidence_11B.PNG">
</p>

**C-.We need to know what is the correlation of high and volumen**<br>
```scala
df.select(corr("High","Volume").alias("Correlacion")).show()

```
<p>
  <img alt="Evidence1" src="./../Media/Evidence_11C.PNG">
</p>

**D-.We need to know which are the maximun for each year**<br>
```scala
df.groupBy(year(df("Date")).alias("Year")).max("High").sort(asc("Year")).show()

```
<p>
  <img alt="Evidence1" src="./../Media/Evidence_11D.PNG">
</p>

**E-.This question deals with knowing the average close for each month**<br>
```scala
df.groupBy(month(df("Date")).alias("Month")).avg("Close").sort(asc("Month")).show()

```
<p>
  <img alt="Evidence1" src="./../Media/Evidence_11E.PNG">
</p>

### Conclusion
El manejo de datos y la interpretacion es una parte fundamental para sacar conclusiones de
la informacion para poder tomar una descicion respecto a esa informacion proporcionada en
la practica apredimos que al realizar las operaciones podemos darnos cuenta de que nos 
proporciona informacion util para cada uno de las preguntas, lo mas complicado de realizar 
seria quizas las utilimas dos pregunta ya que pedia agrupar pero no fue caso solo era cuestion
de darle sentido a la operacion que queriamos realizar y con esto aprendimos a como manejar 
un dataframe desde importarlo, leerlo e interpretarlo, asi como crear uno nuevo con nuevas 
adiciones.