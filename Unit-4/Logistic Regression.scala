import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession

import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

val spark = SparkSession.builder().getOrCreate()

val data  = spark.read.option("header","true").option("inferSchema", "true").format("csv").load("bank.csv")

data.printSchema()

// Imprima un renglon de ejemplo 

data.head(1)
val colnames = data.columns
val firstrow = data.head(1)(0)
println("\n")
println("Example data row")
for(ind <- Range(1, colnames.length)){
    println(colnames(ind))
    println(firstrow(ind))
    println("\n")
}

//// Preparar el DataFrame para Machine Learning ////


val logregdata = timedata.select(data("default").as("label"), $"age", $"job", $"marital", $"education", $"balance", $"campaign")

// Cree un nuevo objecto VectorAssembler llamado assembler para los feature
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors

//val assembler = (new VectorAssembler().setInputCols(Array("Daily Time Spent on Site", "Age","Area Income","Daily Internet Usage","Hour","Male")).setOutputCol("features"))
val assembler = (new VectorAssembler().setInputCols(Array("age", "job", "marital", "education", "balance", "campaign")).setOutputCol("features"))



// Utilice randomSplit para crear datos de train y test divididos en 70/30
val Array(training, test) = logregdata.randomSplit(Array(0.7, 0.3), seed = 12345)


// Configure un Pipeline ///////

// Cree un nuevo objeto de  LogisticRegression llamado lr

// Cree un nuevo  pipeline con los elementos: assembler, lr

// Ajuste (fit) el pipeline para el conjunto de training.


// Tome los Resultados en el conjuto Test con transform

import org.apache.spark.ml.Pipeline

val lr = new LogisticRegression()

val pipeline = new Pipeline().setStages(Array(assembler, lr))

val model = pipeline.fit(training)

val results = model.transform(test)

//// Evaluacion del modelo /////////////

// Convierta los resutalos de prueba (test) en RDD utilizando .as y .rdd
// Inicialice un objeto MulticlassMetrics 
// Imprima la  Confusion matrix
import org.apache.spark.mllib.evaluation.MulticlassMetrics

val predictionAndLabels = results.select($"prediction",$"label").as[(Double, Double)].rdd
val metrics = new MulticlassMetrics(predictionAndLabels)

println("Confusion matrix:")
println(metrics.confusionMatrix)

metrics.accuracy