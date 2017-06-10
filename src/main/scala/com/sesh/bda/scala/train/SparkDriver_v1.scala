  package com.sesh.bda.scala.train

  import java.util.Calendar

  import com.sesh.bda.scala.common.SparkCommonUtils
  import com.sun.javafx.util.Logging
  import org.apache.spark.rdd.RDD
  import org.apache.spark.sql.{DataFrame, SparkSession}
  import org.apache.spark.{SparkConf, SparkContext}
  import org.slf4j.Logger

  object SparkDriver_v1  {
    def main(args: Array[String]): Unit = {

      import com.sesh.bda.scala.common._
      import org.apache.spark.sql.functions._
      import org.apache.log4j.Logger
      import org.apache.log4j.Level

      Logger.getLogger("org").setLevel(Level.ERROR)
      Logger.getLogger("akka").setLevel(Level.ERROR)

      val spSession = SparkCommonUtils.spSession
      val spContext = SparkCommonUtils.spContext
      val datadir = SparkCommonUtils.datadir
      val customerJson = datadir + "customerData.json"
      val deptJson = datadir + "department.json"
      // val filePath = "file:/PoC/Poc_v1/scala-spark-bda/data-files/ReadAllQueries.txt"
      val filePath = datadir + "ReadAllQueries.txt"

      val properties: Map[String, String] = SLPattern.loadProperties(filePath, spContext)
      println(properties("GenderQuery"))
      println(properties("ProfQuery"))
      println(properties("OrdersQuery"))

      // for ((k, v) <- properties) printf("key: %s, value: %s\n", k, v)

      for ((k, v) <- properties) printf("key: %s, value: %s\n", k, v)

      //  properties.foreach(println)

      /*

      Read Properties file & hold it in a MAP collection --
      The file path is local for now. In prod- it  points to hdfs location
      The file is Json again

        SLPattern.loadProperties(filePath,spSession):Map[String,String]




       */

      /*-------------------------------------------------------------------
     * Problem Decomposition
     -------------------------------------------------------------------*/

      val RetailInput = 'Y'

      if (RetailInput == 'Y') {

        /*

        If Retail Input is Y , the retail process is invoked
         */

        val (aggDF: DataFrame, avgDF: DataFrame) = Customer.process(spSession, customerJson, deptJson)

        println("Customer.process is completed at time ? ")

        aggDF.show()
        avgDF.show()

      }
      else {
        println("The retail code is not executed")
      }

    }


  }


  // Control Shift R

  object Customer {

    def process(spSession: SparkSession, customerJson: String, deptJson: String) = {
      val customerDF = ReadDataFrame.JSON(spSession, customerJson)

      val deptDF = ReadDataFrame.JSON(spSession, deptJson)

      customerEnriched(customerDF, deptDF)


    }

    def customerEnriched(customerDF: DataFrame, deptDF: DataFrame) = {
      val aggDeptCountDF = customerDF.filter(customerDF("age") > 30).
        join(deptDF, customerDF("deptid") === deptDF("deptid"))
        .groupBy(customerDF("deptid")).count()

      val maxDeptAgeDF = customerDF.filter(customerDF("age") > 30).
        join(deptDF, customerDF("deptid") === deptDF("deptid"))
        .groupBy(customerDF("deptid")).max("age")

      (aggDeptCountDF, maxDeptAgeDF)

    }
  }

  object ReadDataFrame {

    def JSON(spSession: SparkSession, customerJson: String): DataFrame = {
      spSession.read.json(customerJson)

    }

      def CSV(spSession: SparkSession, customerJson: String): DataFrame = {
        spSession.read.json(customerJson)
        //Databricks api goes here


      }


    }


    object SLPattern {

      def loadProperties(filePath: String, spContext: SparkContext): Map[String, String] = {

        val prop = spContext.textFile(filePath).map(x => {
          val y = x.split("=>")
          (y(0), y(1))
        }).collect.toMap
        prop
      }

    }














