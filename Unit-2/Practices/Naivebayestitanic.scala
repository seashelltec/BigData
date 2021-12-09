import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, VectorAssembler}
import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.sql.SparkSession
//Cargar datos
val spark = SparkSession.builder().getOrCreate()
val dat = spark.read.option("header", "true").option("inferSchema","true")csv("Titanic.csv")
dat.show()


//Borrar columnas innecesarias
val data = dat.drop("PassengerId","Name","Sex","Parch","Ticket","Cabin","Embarked","sibSp")
data.show()

//Limpiar dataset     //isex
val featureIndexer = new VectorAssembler().setInputCols(Array("Pclass","Fare","Age")).setOutputCol("features")
val dt = featureIndexer.transform(data)
dt.select("Survived","Pclass","Age","Fare","features").show()


//Separar dataset
val Array(trainingData, testData) = dt.randomSplit(Array(0.7, 0.3), seed = 1234L)
testData
val labelmodel = new NaiveBayes().setLabelCol("Survived").setFeaturesCol("features")
val model = labelmodel.fit(trainingData)


// Select example rows to display.
val predictions = model.transform(testData)
predictions.show()

// Select (prediction, true label) and compute test error
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("Survived").setPredictionCol("prediction").setMetricName("accuracy")
val accuracy = evaluator.evaluate(predictions)
println(s"Test set accuracy = $accuracy")