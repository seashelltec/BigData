//Import the library

import org.apache.spark.sql.types.DoubleType
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, VectorAssembler}
import org.apache.spark.ml.feature.IndexToString
import org.apache.spark.ml.Pipeline

//1-.Load the dataframe iris
val iris=spark.read.format("csv").option("header","true").load("iris.csv")

//Clean the dataframe
val df = iris.withColumn("sepal_length", $"sepal_length".cast(DoubleType)).withColumn("sepal_width", $"sepal_width".cast(DoubleType)).withColumn("petal_length", $"petal_length".cast(DoubleType)).withColumn("petal_width", $"petal_width".cast(DoubleType))

//2-.Colum names
df.columns

//3-.Scheme is like
df.printSchema()

//4-.Only the 5 first columns
df.show(5)

//5-.Use the describe method 
df.describe().show()

//6-.Change the the category data for eti

val assemler = new VectorAssembler().setInputCols(Array("sepal_length","sepal_width","petal_length","petal_width")).setOutputCol("features")
var features = assemler.transform(df)

val indexerL = new StringIndexer().setInputCol("species").setOutputCol("label").fit(features)
val indexerF = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(features)


val splits = df.randomSplit(Array(0.6, 0.4), seed = 1234L)
val train = splits(0)
val test = splits(1)

val layers = Array[Int](4, 5, 5, 3)

//7-.Clasification model

val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures").setBlockSize(128).setSeed(System.currentTimeMillis).setMaxIter(200)

val convertLabel = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(indexerL.labels)
val pipelin = new Pipeline().setStages(Array(indexerL, indexerF, trainer, convertLabel))

val model = pipelin.fit(train) // ERROR DON'T EXIST features

//8-.The model results
val result = model.transform(test).select(col("*"),col("prediction").as("predictionIndex", data.schema("label").metadata))
val labelConverter = new IndexToString().setInputCol("predictionIndex").setOutputCol("predictedLabel")
val prediction = convertLabel.transform(result)
val labelsPrediction = prediction.select("species","predictedLabel")
labelsPrediction.show(150)
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
println(s"Test set accuracy = ${evaluator.evaluate(labelsPrediction)}")

//8. Print the model results
val predictions = model.transform(testData)

predictions.show(5)

import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
val accuracy = evaluator.evaluate(predictions)
println("Test Error = " + (1.0 - accuracy))