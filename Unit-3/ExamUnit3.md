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

<H2><p align="Center">Practice evaluatoria 3</p></H2>

<H2><p align="Center">Unidad 3</p></H2>

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>



### Introduction 

This proyect is a form to can manipulare the data frame, well the data and can get results and to do comparations and can to do probability in a future, we can see the information on  orderly manner so important for can see best the results and the easily way.


The first part is import the library you need in this case is soark session for the sesion spark like befoore we see, aftter log  for to do more small the wrong, and import the library for kmeans for the model, the last thing is load the dataset.

```scala
// 1-. Importar una simple sesión Spark.
import org.apache.spark.sql.SparkSession

// 2-. Utilice las lineas de código para minimizar errores
import org.apache.log4j.
Logger.getLogger("org").setLevel(Level.ERROR)

// 3-. Cree una instancia de la sesión Spark
val spark = SparkSession.builder().getOrCreate()

// 4-. Importar la librería de Kmeans para el algoritmo de agrupamiento.
import org.apache.spark.ml.clustering.KMeans

// 5-. Carga el dataset de Wholesale Customers Data
val dataset = spark.read.option("header","true").option("inferSchema","true").format("csv").load("Wholesale customers data.csv")
```
<p>
<img alt="Logo" src="./../Unit-3/Media/Ex3-1.PNG" >
</p>

For these practice we need to select some columuns in spesific

```scala
// 6-. Seleccione las siguientes columnas: Fresh, Milk, Grocery, Frozen, Detergents_Paper, Delicassen y llamar a este conjunto feature_data
val feature_data = (dataset.select($"Fresh", $"Milk", $"Grocery", $"Frozen", $"Detergents_Paper", $"Delicassen"))
```
<p>
<img alt="Logo" src="./../Unit-3/Media/Ex3-2.PNG" >
</p>

Well here only import the library for to manipulate vectors and assembly, and used them

```scala
// 7-. Importar Vector Assembler y Vector
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors

//8-.Crea un nuevo objeto Vector Assembler para las columnas de caracteristicas como un conjunto de entrada, recordando que no hay etiquetas
val assembler = new VectorAssembler().setInputCols(Array("Fresh","Milk","Grocery","Frozen","Detergents_Paper","Delicassen")).setOutputCol("features")
```
<p>
<img alt="Logo" src="./../Unit-3/Media/Ex3-3.PNG" >
</p>

Withe the object create we go to transform, used transform and save in features, the other thing is used the model kmeans

```scala
//9-.Utilice el objeto assembler para transformar feature_data
val  features = assembler.transform(feature_data)

//10-.Crear un modelo Kmeans con K=3
val kmeans = new KMeans().setK(3).setSeed(1L) 
val model = kmeans.fit(features)
```

<p>
<img alt="Logo" src="./../Unit-3/Media/Ex3-4.PNG" >
</p>

Here only need to evalute the model and print the results

```scala
//11-.Evalúe los grupos utilizando Within Set Sum of Squared Errors WSSSE e imprima los centroides.
val WSSSE = model.computeCost(features)
println(s"Within set sum of Squared Errors = $WSSSE")
```
<p>
<img alt="Logo" src="./../Unit-3/Media/Ex3-5.PNG" >
</p>

And the last thing is only print the centers

```scala
println("Cluster Centers: ") 
model.clusterCenters.foreach(println)
```
<p>
<img alt="Logo" src="./../Unit-3/Media/Ex3-6.PNG" >
</p>



### Conclusion

After to create the model and work with the data, is clear the things than plain sight we cant se all information more clear withe thigs than really important you, and to do comparate withe the other results.

When you to do this type of analics you can learn more about the data, you can see the information, because is different the data and the result, and if you made this model is more easy to read the important information.

