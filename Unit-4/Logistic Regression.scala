//importacion de la libreria logisticRegression
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer, VectorIndexer}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession

import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

//Creacion de una sesion de spark
val spark = SparkSession.builder().getOrCreate()

//Carga del repositorio de datos
val data = spark.read.option("header","true").option("inferSchema", "true").option("deimiter",";").format("csv").load("bank.csv")


//Categorizacion de las variables de tipo string a variables numericas 
val data1 = data.withColumn("y",when(col("y").equalTo("yes"),1).otherwise(col("y")))
val data2 = data1.withColumn("y",when(col("y").equalTo("no"),2).otherwise(col("y")))
val newdata = data2.withColumn("y",'y.cast("Int"))

// Creacion del vector en base a los features

val assembler = (new VectorAssembler().setInputCols(Array("age", "balance", "day","duration")).setOutputCol("features"))


//transformacion del dataframe con el vector
val data2 = assembler.transform(newdata)

//Renombrar columnas del dataframe
val featuresLabel = data2.withColumnRenamed("y", "label")

//seleccion de las columnas principales
val finaldata = featuresLabel.select("label","features")

// division del data en training y test 70 y 30
val Array(training, test) = finaldata.randomSplit(Array(0.7, 0.3), seed = 1234)


//Modelo de regresion

val lr = new LogisticRegression()

val model = lr.fit(training)

val results = model.transform(test)

//Resultados del modelo

import org.apache.spark.mllib.evaluation.MulticlassMetrics

//conversion de resultados 
val predictionAndLabels = results.select($"prediction",$"label").as[(Double, Double)].rdd
val metrics = new MulticlassMetrics(predictionAndLabels)

println("Confusion matrix:")
println(metrics.confusionMatrix)

//resultado de la presicion
metrics.accuracy
println(s"Accuracy=${metrics.accuracy}")

//para ciclos
for(v <- 0 to 29){

}
