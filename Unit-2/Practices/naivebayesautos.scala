import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, VectorAssembler}
import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.sql.SparkSession
//Cargar datos
val spark = SparkSession.builder().getOrCreate()
val dat = spark.read.option("header", "true").option("inferSchema","true")csv("vehicleh.csv")
dat.show()


//Borrar columnas innecesarias
val data = dat.drop("brand","time","color")
data.show()

//Limpiar dataset     //isex
val featureIndexer = new VectorAssembler().setInputCols(Array("stoled")).setOutputCol("features")
val dt = featureIndexer.transform(dat)
dt.select("number_plate","stoled","features").show()

//Separar dataset
val Array(trainingData, testData) = dt.randomSplit(Array(0.7, 0.3))
testData
val labelmodel = new NaiveBayes().setLabelCol("number_plate").setFeaturesCol("features")
val model = labelmodel.fit(testData)


// Select example rows to display.
val predictions = model.transform(testData)
predictions.show()

// Select (prediction, true label) and compute test error
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("number_plate").setPredictionCol("prediction").setMetricName("accuracy")
val accuracy = evaluator.evaluate(predictions)
println(s"Test set accuracy = $accuracy")



//original
from pyspark.sql import SparkSession 
from pyspark.sql.functions import * 
from pyspark.ml import Pipeline 
from pyspark.ml.feature import VectorAssembler 
from pyspark.ml.feature import StringIndexer 
from pyspark.ml.classification import NaiveBayes 
from pyspark.ml.evaluation import MulticlassClassificationEvaluator
# Read data from the vehicle_stolen_dataset.csv 
vehicle_data = pd.read_csv(“./vehicle_stolen_dataset.csv”, header=None) 
#Sets the Spark master URL to run locally. 
spark = SparkSession.builder.master("local[*]").getOrCreate() 
#Create DataFrame 
vehicle_df = spark.createDataFrame(vehicle_data) 
vehicle_df.show(5)
vehicle_df = vehicle_df.select(col("0").alias("number_plate"),  col("1").alias("brand"),  
col("2").alias("color"),  
col("3").alias("time"),  
col("4").alias("stoled"))
indexers = [
StringIndexer(inputCol="brand", outputCol = "brand_index"),  
StringIndexer(inputCol="color", outputCol = "color_index"),  StringIndexer(inputCol="time", outputCol = "time_index"),  StringIndexer(inputCol="stoled", outputCol = "label")]
pipeline = Pipeline(stages=indexers) 
#Fitting a model to the input dataset. 
indexed_vehicle_df = pipeline.fit(vehicle_df).transform(vehicle_df) 
indexed_vehicle_df.show(5,False) 
#We have given False for turn off default truncation
vectorAssembler = VectorAssembler(inputCols = [“brand_index”, “color_index”, “tim e_index”],outputCol = “features”) 
vindexed_vehicle_df = vectorAssembler.transform(indexed_vehicle_df) 
vindexed_vehicle_df.show(5, False)
splits = vindexed_vehicle_df.randomSplit([0.6,0.4], 42) 
# optional value 42 is seed for sampling 
train_df = splits[0] 
test_df = splits[1]
nb = NaiveBayes(modelType=”multinomial”)
nbmodel = nb.fit(train_df)
predictions_df = nbmodel.transform(test_df)
predictions_df.show(5, True)
evaluator = MulticlassClassificationEvaluator(labelCol="label", predictionCol="pr ediction", metricName="accuracy") 
nbaccuracy = evaluator.evaluate(predictions_df) 
print("Test accuracy = " + str(nbaccuracy))