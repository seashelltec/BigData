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

<H2><p align="Center">Practice 6</p></H2>

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
Like all practices we start with the library is need for this practice 

```scala
import org.apache.spark.ml.classification.LinearSVC
```
<br>
Here only load the dataframe in the ariable training 

```scala
val training = spark.read.format("libsvm").load("SampleLivs.txt")
```
<br>

Here we define the maximum number of iterations and the regularization parameter
```scala
val lsv = new LinearSVC().setMaxIter(10).setRegParam(0.1)
```
<br>

Adnd here fit a model to the input data
```scala
val lsvModel = lsv.fit(training)
```
<br>

The last thing for to do is print the results

```scala
println(s"coefficients: ${lsvModel.coefficients} intercept: ${lsvModel.intercept}")
```