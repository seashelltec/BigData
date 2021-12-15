//Importacion de librerias de multilayerperceptron
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.sql.types.DateType
import org.apache.spark.ml.feature.VectorIndexer
import org.apache.spark.ml.feature.IndexToString
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.Pipeline
import org.apache.log4j._   

//minimizacion de errores
Logger.getLogger("org").setLevel(Level.ERROR)

//creacion de una sesision de spark.

val spark = SparkSession.builder().getOrCreate()

//carga del repositorio de datos 
val data = spark.read.option("header","true").option("inferSchema","true").option("delimiter",";").format("csv").load("bank.csv")

dataframe.head()
dataframe.describe()

//Categoriazacion de datos string a numericos
val data1 = data.withColumn("y",when(col("y").equalTo("yes"),1).otherwise(col("y")))
val data2 = data1.withColumn("y",when(col("y").equalTo("no"),0).otherwise(col("y")))
val newdata = data2.withColumn("y",'y.cast("Int"))



//Creacion del vector para la asignacion al features
val assembler = (new VectorAssembler().setInputCols(Array("balance", "day","duration","pdays","previous")).setOutputCol("features"))
val newframe = assembler.transform(newdata)

//Renombrar columnas del dataframe
val featuresLabel = newframe.withColumnRenamed("y", "label")

//seleccion de las columnas principales
val finaldata = featuresLabel.select("label","features")



//cambio de la etiqueta principal
val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(finaldata)
//cambio del nombre a features
val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(2).fit(finaldata)

// division del data en train y test
val splits = finaldata.randomSplit(Array(0.7, 0.3), seed = 1234L)
val trainingData = splits(0)
val testData = splits(1)

//Especificacion de la red neuronal
val layers = Array[Int](4, 5, 4, 3)

// creacion de los parametros del trainer
val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)

//Conversion de retorno de label
val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labels)

//encadenamiento del indexador y modelo multilayer en una pipeline
val pipeline = new Pipeline().setStages(Array(labelIndexer, featureIndexer, trainer, labelConverter))

// modelo del trainingData
val model = pipeline.fit(trainingData)

// resultados de precision de modelo y error
val prediction = model.transform(testData)
prediction.select("prediction", "label","features").show(5)

//val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")

val accuracy = evaluator.evaluate(prediction)



println("accuracy = "+ accuracy)
println("Test Error = " + (1.0 - accuracy))


println(s"Test set accuracy = ${evaluator.evaluate(predictionAndLabels)}")


//
//https://databricks-prod-cloudfront.cloud.databricks.com/public/4027ec902e239c93eaaa8714f173bcfc/3741049972324885/1019862370390522/4413065072037724/latest.html