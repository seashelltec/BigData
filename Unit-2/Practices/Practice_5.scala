import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

// Load the data stored in LIBSVM format as a DataFrame.
val data = spark.read.format("libsvm").load("SampleLivs.txt")


val splits = data.randomSplit(Array(0.6, 0.4), seed = 1503L)
val train = splits(0)
val test = splits(1)
val layers = Array[Int](8, 5, 4, 8)


val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(130).setSeed(1503L).setMaxIter(100)
val model = trainer.fit(train)

val result = model.transform(test)
val predictLabels = result.select("prediction", "label")
val eval = new MulticlassClassificationEvaluator().setMetricName("accuracy")



println(s"Test:  ${eval.evaluate(predictLabels)}")
