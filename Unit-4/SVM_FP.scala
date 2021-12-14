import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.feature.StringIndexer 
import org.apache.spark.ml.Pipeline

//Minimize errors
import org.apache.log4j._   
Logger.getLogger("org").setLevel(Level.ERROR)

//Spark session.
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()

//Load the dataset
val dataframe = spark.read.option("header","true").option("inferSchema","true").option("delimiter",";").format("csv").load("iris.csv")
dataframe.head()
dataframe.describe()

//Transform categorical data to numeric
val stringindexer = new StringIndexer().setInputCol("species").setOutputCol("label")
val df = stringindexer.fit(dataframe).transform(dataframe)

//Create a vector with the select columns
val assembler = new VectorAssembler().setInputCols(Array("sepal_length","sepal_width","petal_length","petal_width")).setOutputCol("features")
val output = assembler.transform(df)

//Transform features
val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(output)
val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(output)

//Training example data and train
val Array(trainingData, testData) = output.randomSplit(Array(0.7, 0.3), seed = 1234L)

// Training a model
val dt = new DecisionTreeClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures")

// Convert indexed labels back to original labels.
val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labels)

// Chain indexers and tree in a Pipeline.
val pipeline = new Pipeline().setStages(Array(labelIndexer, featureIndexer, dt, labelConverter))

// Training model
val model = pipeline.fit(trainingData)

// Make predictions
val predictions = model.transform(testData)

// Select example rows to display.
predictions.select("predictedLabel", "label", "features").show(5)

// Select (prediction, true label) and compute test error.
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")

val accuracy = evaluator.evaluate(predictions)
println(s"Test error = ${(1.0 - accuracy)}")
