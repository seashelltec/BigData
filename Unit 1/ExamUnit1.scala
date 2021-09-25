//Exam unit 1

//1-.Comienza una simple sesión Spark.
//2-.Cargue el archivo Netflix Stock CSV, haga que Spark infiera los tipos de datos.
import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema","true")csv("Netflix_2011_2016.csv")
df
df.printSchema()

//3-.¿Cuáles son los nombres de las columnas?
df.columns

//4-.¿Cómo es el esquema?
df.printSchema()

//5-.Imprime las primeras 5 columnas.
for(row <- df.head(5)){
    println(row)
}

//6-.Usa describe () para aprender sobre el DataFrame.
df.describe().show()

//7-.Crea un nuevo dataframe con una columna nueva llamada “HV Ratio” que es la relación que
//esixte entre el precio de la columna “High” frente a la columna “Volumen” de acciones
//negociadas por un día. Hint es una operación
import org.apache.spark.sql.Column
val newData = df.withColumn("HVRatio", df("High")/df("Volume"))
newData.show()

//8-.¿Qué día tuvo el pico mas alto en la columna “Open”?
val maxp = newData.orderBy(desc("Open"))
maxp.select("Date").limit(1).show()

//9-.¿Cuál es el significado de la columna Cerrar “Close” en el contexto de información financiera,
//expliquelo no hay que codificar nada?
/*newData.orderBy(desc("Close")).show()

Cuando el precio de la columna High sube parece ser lo mismo para la columna Close solo que esta 
siempre en menor cantidad que la High, lo que quiere decir que a medida que High
sube lo mas probables es que Close tambien lo haga pero en menor cantidad*/

//10-.¿Cuál es el máximo y mínimo de la columna “Volumen”?
df.select(max("Volume")).show()
df.select(min("Volume")).show()

//11-.Con Sintaxis Scala/Spark $ conteste los siguiente:
df.filter($"Close"<600).count()
