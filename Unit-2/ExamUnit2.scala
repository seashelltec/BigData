//Import the library

import org.apache.spark.sql.types
import org.apache.spark.sql.functions

//1-.Load the dataframe iris
val iris=spark.read.format("csv").option("header","true").load("iris.csv")

//Clean the dataframe
val df = iris.withColumn("sepal_length", $"sepal_length".cast(DoubleType)).withColumn("sepal_width", $"sepal_width".cast(DoubleType)).withColumn("petal_length", $"petal_length".cast(DoubleType)).withColumn("petal_width", $"petal_width".cast(DoubleType))

//2-.Colum names
data.columns

//3-.Scheme is like
data.printSchema()

//4-.Only the 5 first columns
data.show(5)

//5-.Use the describe method 
data.describe().show()