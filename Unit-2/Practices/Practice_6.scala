
import org.apache.spark.ml.classification.LinearSVC

 // Load the data stored in LIBSVM format as a DataFrame.
val training = spark.read.format("libsvm").load("SampleLivs.txt")

// setMaxIter: Set the maximum number of iterations
// setRegParam: Set the regularization parameter
val lsv = new LinearSVC().setMaxIter(10).setRegParam(0.1)

// Fit a model to the input data
val lsvModel = lsv.fit(training)

// Print the coefficients and intercept for linear svc
println(s"coefficients: ${lsvModel.coefficients} intercept: ${lsvModel.intercept}")