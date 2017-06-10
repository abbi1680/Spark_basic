	/****************************************************************************

					Author : 					Seshi

							 Test POC - This is utility class used in entire Spark App

	*****************************************************************************/

	package com.sesh.bda.scala.common

	object SparkCommonUtils {

		import org.apache.spark.sql.SparkSession
		import org.apache.spark.SparkContext
		import org.apache.spark.SparkConf
		import org.apache.spark.sql.SQLContext
  //  import org.apache.spark.sql.hive._
		//Directory where the data files for the examples exist.
		val datadir = "/PoC/Poc_v1/scala-spark-bda/data-files/"

		//A name for the spark instance. Can be any string
		val appName="Poc Project"
		//Pointer / URL to the Spark instance - embedded
		val sparkMasterURL = "local[2]"
		//Temp dir required for Spark SQL
		val tempDir= "file:////PoC/Poc_v1/scala-spark-bda"



		//val sqlContext = new org.apache.spark.sql.SQLContext(sc)
			//Initialization. Runs when object is created

			//Need to set hadoop.home.dir to avoid errors during startup
			System.setProperty("hadoop.home.dir",
					"c:\\spark\\winutils\\");

			//Create spark configuration object
			val conf = new SparkConf()
				.setAppName(appName)
				.setMaster(sparkMasterURL)
				.set("spark.executor.memory","2g")
				.set("spark.sql.shuffle.partitions","2")

			val spContext:SparkContext = SparkContext.getOrCreate(conf)
			val sqlContext:SQLContext =new org.apache.spark.sql.SQLContext(spContext)
		var spSession:SparkSession = SparkSession
			.builder()
			.appName(appName)
			.master(sparkMasterURL)
			.config("spark.sql.warehouse.dir", tempDir)
			.getOrCreate()



	}