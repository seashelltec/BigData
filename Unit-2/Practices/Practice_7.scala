import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

 // Load the data stored in LIBSVM format as a DataFrame.
val data = spark.read.format("libsvm").load("SampleLivs.txt")
