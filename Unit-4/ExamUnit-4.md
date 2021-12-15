<p align="center">
    <img alt="Logo" src="https://www.tijuana.tecnm.mx/wp-content/uploads/2021/08/liston-de-logos-oficiales-educacion-tecnm-FEB-2021.jpg" width=850 height=250>
</p>

<H2><p align="Center">TECNOLÓGICO NACIONAL DE MÉXICO</p></H2>

<H2><p align="Center">INSTITUTO TECNOLÓGICO DE TIJUANA</p></H2>

<H2><p align="Center">SUBDIRECCIÓN ACADÉMICA</p></H2>

<H2><p align="Center">DEPARTAMENTO DE SISTEMAS Y COMPUTACIÓN</p></H2>

<H2><p align="Center">NOMBRE DE LOS ALUMNOS: </p></H2>

<H2><p align="Center">GALAVIZ LONA OSCAR EDUARDO (N.CONTROL: 17212993)</p></H2>

<H2><p align="Center">MARQUEZ MILLAN SEASHELL VANESSA (17212153 ) </p></H2>

<H2><p align="Center">Carrera: Ingeniería Informática</p></H2>

<H2><p align="Center">Semestre: 9no </p></H2>

<H2><p align="Center">MATERIA: Datos Masivos</p></H2>

<H2><p align="Center">PROFESOR: JOSE CHRISTIAN ROMERO HERNANDEZ</p></H2>

<H2><p align="Center">Practice evaluatoria 4</p></H2>

<H2><p align="Center">Unidad 4</p></H2>

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>



### Introduction 

This practice is for can see the best model for this example the data set is bank.csv is a data set about  related with direct marketing campaigns (phone calls) of a Portuguese banking institution. The classification goal is to predict if the client will subscribe a term deposit (variable y) and we trai to do a prediction with teh models:
<br>

- SVM
- Decision Three
- Logistic Regresion
- Multilayer perceptron

Start talk about the each of the models like SVM is a supervised machine learning algorithm that can be used for both classification or regression challenges. However,  it is mostly used in classification problems. In the SVM algorithm, we plot each data item as a point in n-dimensional space (where n is a number of features you have) with the value of each feature being the value of a particular coordinate. Then, we perform classification by finding the hyper-plane that differentiates the two classes very well, now let's move on to Decision three are a non-parametric supervised learning method used for classification and regression. The goal is to create a model that predicts the value of a target variable by learning simple decision rules inferred from the data features. A tree can be seen as a piecewise constant approximation, decision trees learn from data to approximate a sine curve with a set of if-then-else decision rules. The deeper the tree, the more complex the decision rules and the fitter the model, while logistic regresion is a supervised learning classification algorithm used to predict the probability of a target variable. The nature of target or dependent variable is dichotomous, which means there would be only two possible classes so the dependent variable is binary in nature having data coded as either 1 (stands for success/yes) or 0 (stands for failure/no) and multilayer perception is a neural network where the mapping between inputs and output is non-linear, has input and output layers, and one or more hidden layers with many neurons stacked together. And while in the Perceptron the neuron must have an activation function that imposes a threshold, like ReLU or sigmoid, neurons in a Multilayer Perceptron can use any arbitrary activation function.

### Developement

**SVM**

As we sai some steps on the code is so similar so here only explain the specific steps like these is where define the model, we define the max iteraccion and the param is go to train with differents.

```scala
val supportVM = new LinearSVC().setMaxIter(10).setRegParam(0.1)
```

Here train to model and need to transfor the test after we see the prediction

```scala
val modelSVM = supportVM.fit(training)

val predictions = modelSVM.transform(test)
predictions.show()
```

Here is a so important step because is the result and to do de metrics with the prediction, too to do the cofusion matrix for to do more real the results and print the accuacy and test error

```scala
val predictionAndLabels = predictions.select($"prediction",$"label").as[(Double, Double)].rdd
val metrics = new MulticlassMetrics(predictionAndLabels)

println("Confusion matrix:")
println(metrics.confusionMatrix)

println("Accuracy: " + metrics.accuracy) 
println(s"Tst Error = ${(1.0 - metrics.accuracy)}")
spark.stop()
```

**Results SVM**

| Iter | SVM | 
| ------------- | ------------- |
| 1  | 0.8833 |
| 2  | 0.8835 |
| 3  | 0.8877 |
| 4  | 0.8820 |
| 5  | 0.8836 |
| 6  | 0.8865 |
| 7  | 0.8846 |
| 8  | 0.8797 |
| 9  | 0.8837 |
| 10 | 0.8838 |
| 11 | 0.8799 |
| 12 | 0.8839 |
| 13 | 0.8805 |
| 14 | 0.8830 |
| 15 | 0.8818 |
| 16 | 0.8805 |
| 17 | 0.8852 |
| 18 | 0.8811 |
| 19 | 0.8836 |
| 20 | 0.8796 |
| 21 | 0.8874 |
| 22 | 0.8865 |
| 23 | 0.8819 |
| 24 | 0.8823 |
| 25 | 0.8841 |
| 26 | 0.8878 |
| 27 | 0.8793 |
| 28 | 0.8874 |
| 29 | 0.8856 |
| 30 | 0.8842 |

**AVERAGE: 0.883466667** 

**DesicionTree**

In these case start with the train of the model and we convert the the news labels too after start with pipeline than we help to do the predictions.

```scala
val dt = new DecisionTreeClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures")

val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labels)
val pipeline = new Pipeline().setStages(Array(labelIndexer, featureIndexer, dt, labelConverter))

```

An this is the final is the results, here used the pipeline and the trainingdata, after to do the prediction with the test data  after print the prediction, the last part is evalute the prediction and print the accuracy and test error.

```scala
val model = pipeline.fit(trainingData)

val predictions = model.transform(testData)
predictions.select("predictedLabel", "label", "features").show(5)

val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
val accuracy = evaluator.evaluate(predictions)
println(s"Test error = ${(1.0 - accuracy)}")
```


| Iter | DescTree | 
| ------------- | ------------- |
| 1  | 0.8907 |
| 2  | 0.8951 |
| 3  | 0.8940 |
| 4  | 0.8901 |
| 5  | 0.8867 |
| 6  | 0.8809 |
| 7  | 0.8887 |
| 8  | 0.8903 |
| 9  | 0.8899 |
| 10 | 0.8804 |
| 11 | 0.8874 |
| 12 | 0.8856 |
| 13 | 0.8912 |
| 14 | 0.8903 |
| 15 | 0.8878 |
| 16 | 0.8908 |
| 17 | 0.8856 |
| 18 | 0.8889 |
| 19 | 0.8875 |
| 20 | 0.8806 |
| 21 | 0.8874 |
| 22 | 0.8803 |
| 23 | 0.8907 |
| 24 | 0.8845 |
| 25 | 0.8874 |
| 26 | 0.8899 |
| 27 | 0.8867 |
| 28 | 0.8907 |
| 29 | 0.8887 |
| 30 | 0.8840 |

**AVERAGE: 0.88776** 