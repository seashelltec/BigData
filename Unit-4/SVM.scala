import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.classification.LinearSVC
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorIndexer
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.Pipeline

//Minimize errors
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

//Spark session
val spark = SparkSession.builder.appName("svm").getOrCreate()

//Load the dataset
val df  = spark.read.option("header","true").option("inferSchema", "true").option("delimiter",";").format("csv").load("bank-full.csv")
df.head()
df.describe()

//Infex column
val labelIndexer = new StringIndexer().setInputCol("y").setOutputCol("indexedY").fit(df)
val indexed = labelIndexer.transform(df).drop("y").withColumnRenamed("indexedY", "label")

//Create a vector with the colum
val vectorFeatures = (new VectorAssembler().setInputCols(Array("balance","day","duration","pdays","previous")).setOutputCol("features"))

//Use the assembler object to transform features
val featurestrans = vectorFeatures.transform(indexed)

//Rename column 
val featureslabel = featurestrans.withColumnRenamed("y", "label")

//Union of label and features
val dataindexed = featureslabel.select("label","features")
dataindexed.show()

//Create  labelIndexer and featureIndexer for the pipeline
val labelindexer = new StringIndexer().setInputCol("label").setOutputCol("indexedlabel").fit(dataindexed)
val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedfeatures").setMaxCategories(4).fit(dataindexed)

//Training data 
val Array(training, test) = dataindexed.randomSplit(Array(0.7, 0.3), seed = 1234L)

//Model
val supportVM = new LinearSVC().setMaxIter(10).setRegParam(0.1)
    
//Fitting the trainingData into the model
val modelSVM = supportVM.fit(training)

//Transforming testData for the predictions
val predictions = modelSVM.transform(test)
predictions.show()

//To do the metrics
val predictionAndLabels = predictions.select($"prediction",$"label").as[(Double, Double)].rdd
val metrics = new MulticlassMetrics(predictionAndLabels)

//Confusion matrix.
println("Confusion matrix:")
println(metrics.confusionMatrix)

//Accuracy and Test Error.
println("Accuracy: " + metrics.accuracy) 
println(s"Tst Error = ${(1.0 - metrics.accuracy)}")
spark.stop()