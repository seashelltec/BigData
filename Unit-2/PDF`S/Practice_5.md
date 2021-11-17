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

<H2><p align="Center">Practice 5</p></H2>

<H2><p align="Center">Unidad 2</p></H2>

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

### Development
Well the firt thing is load the library in this case need two

```scala
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

```
<br>

Load the dataframe it's so important too for we can work with te data

```scala
val data = spark.read.format("libsvm").load("SampleLivs.txt")
```
<br>

Now need define the variables than we go to need and these variables are for split the data into train and test

```scala
val splits = data.randomSplit(Array(0.6, 0.4), seed = 1503L)
val train = splits(0)
val test = splits(1)
```
<br>
This is a very important part because here specify layers for the neural network, first nput layer, two intermediate and the last number is output

```scala
val layers = Array[Int](4, 5, 4, 3)
```
<br>

```scala
val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(130).setSeed(1503L).setMaxIter(100)
```
<br>
Here is were train the model 
```scala
val model = trainer.fit(train)
```

<br>
Here we need to compute accuracy on the test set

```scala
val result = model.transform(test)
val predictLabels = result.select("prediction", "label")
val eval = new MulticlassClassificationEvaluator().setMetricName("accuracy")
```
<br>

And this is the finish only print the results

```scala
println(s"Test set accuracy = ${evaluator.evaluate(predictionAndLabels)}")
```