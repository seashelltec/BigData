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

<H2><p align="Center">Examen U2</p></H2>

<H2><p align="Center">Unidad 2</p></H2>

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

### Introduccion
Para poder desarrollor esta practica primero debemos tener en cuenta ciertos puntos importantes en este caso se trata sobre las metodos de clasificacion, asi como tomar algunos puntos anteriores de lo ya antes visto, la clasificacion nos permite identificar o saber en el momento de la busqueda de datos especificos que tan probable es que ese dato sea encontrado o que salga, tambien es utilizado para identificar patrones que sean extraños o muy poco comunes dentro de archivo de datos. Estas clasificaciones ademas de permitinos identificar este tipo de patrones, tambien nos permite saber que exactitud tiene este y cuanta probabildad de margen de error tenga este, con el cual podemos identificar si se puede confiar al 100% del modelo o simplemente tomarlo como una opcion al momento del analisis de datos. Los diferentes modelos funcionan de maneras diferentes pero los datos pueden llegar a una concordancia, se puede decir que los modelos de clasificacion sirve para sacar estadisticas e identificar patrones dentro de las empresas que lo solicitan para poder tener un panorama de como se encuentra una empresa

### Development

**extra-.Import the library
```scala
import org.apache.spark.sql.types.DoubleType
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.IndexToString
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorIndexer
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.feature.IndexToString
import org.apache.spark.ml.Pipeline
```

**1-.Load the data iris csv**
```scala
val iris=spark.read.format("csv").option("header","true").load("iris.csv")
```
<p>
<img alt="Logo" src="./../Unit-2/Media/E2-1.png" >
</p>

**2-.Names colums**
```scala
//Clean the dataframe
val df = iris.withColumn("sepal_length", $"sepal_length".cast(DoubleType)).withColumn("sepal_width", $"sepal_width".cast(DoubleType)).withColumn("petal_length", $"petal_length".cast(DoubleType)).withColumn("petal_width", $"petal_width".cast(DoubleType))

//2-.Colum names
df.columns

```
<p>
<img alt="Logo" src="./../Unit-2/Media/E2-2.png" >
</p>

**3-.Schema**
```scala
df.printSchema()
```
<p>
<img alt="Logo" src="./../Unit-2/Media/E2-3.png" >
</p>

**4-.print first 5 columns**
```scala
df.show(5)
```
<p>
<img alt="Logo" src="./../Unit-2/Media/E2-4.png" >
</p>

**5-.use the method describe**
```scala
df.describe().show()
```
<p>
<img alt="Logo" src="./../Unit-2/Media/E2-5.png" >
</p>

**6-.Transformation and label we have to use**
```scala
val assembler = new VectorAssembler().setInputCols(Array("sepal_length", "sepal_width", "petal_length", "petal_width")).setOutputCol("features")
val features = assembler.transform(df)

val indexerL = new StringIndexer().setInputCol("species").setOutputCol("indexedLabel").fit(features)
val indexerF = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(features)

val splits = features.randomSplit(Array(0.6, 0.4))
val training = splits(0)
val test = splits(1)

val layers = Array[Int](4, 5, 5, 3)
```

**7-.Building model**
```scala
val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures").setBlockSize(128).setSeed(System.currentTimeMillis).setMaxIter(200)
val converterL = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(indexerL.labels)
val pipeline = new Pipeline().setStages(Array(indexerL, indexerF, trainer, converterL))

val model = pipeline.fit(training)
```

**8-. print the result of model**
```scala
val predictions = model.transform(test)

val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol
("prediction").setMetricName("accuracy")
val accuracy = evaluator.evaluate(predictions)
println("Error = " + (1.0 - accuracy))
```
<p>
<img alt="Logo" src="./../Unit-2/Media/E2-6.png" >
</p>

### Conclusion
En el desarrollo de esta practica no fue complicado como tal pero si algo laboriosa porque al momento de querer buscar informacion nos daban resultados de ejemplos de spark pero en python y esto se tenia que transformar a codigo scala pero eso no nos detuvo con la documentacion de spark y los ejemplos de algunos compañeros pudimos resolver todas las cuestiones que se tenian y evidentemente tambien tuvimos que entender y comprender el codigo y lo que estabamos haciendo aunque algunas partes del codigo eran partes que ya teniamos presentes por la unidad pasada en conclusion el poder utilizar estos metodos de clasificacion fue satisfactorio porque aprendimos a utilizar un modelo dentro de un archivo de datos que nos permitia tener un analisis estadistico de estos y para tener un interpretacion de los mismo asi como un panorama mas amplio del mismo identificando patrones o datos sobresalientes