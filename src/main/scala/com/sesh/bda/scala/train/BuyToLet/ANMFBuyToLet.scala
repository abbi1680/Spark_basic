
/** **************************************************************************
  * *
  * Author : 					Seshi
  * *
  * Test POC - This is the core class , which contains the ELT logic
  *
  * ****************************************************************************/

package com.sesh.bda.scala.train.BuyToLet

import java.util.Calendar

import com.sesh.bda.scala.common.SparkCommonUtils
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object ANMFBuyToLet extends BuildContext {

  def runPipeline(part_date: String): (DataFrame, DataFrame) = {
    // The parquet file path is evaluated based on the partition date and execute a Hive context query here
    //      // For Demo - just read the JSON
    val datadir = SparkCommonUtils.datadir

    /*
     These table names are used to build the Parquet path to extract the path from the RAW layer
     The full URI of the HDFS path is used to construct the path and input to the dataframe API
     to pull the dataset.
    */

    val queriesFile = SparkCommonUtils.datadir + "BuyToletProperties.txt"

    val properties: Map[String, String] = loadProperties(queriesFile)

    val anmfQuery: String = properties.get("ANMFHiveQuery").getOrElse("NoQuery")

    println("The anmf query is " + anmfQuery)

    val Customers = "ANMF_Customers.json"
    val Loans = "ANMF_Loans.json"

    val fullFileCustomersURI = datadir + Customers
    val fullFileLoansURI = datadir + Loans

    val CustomersDF: DataFrame = ReadDataFrame.JSON(fullFileCustomersURI)
    val LoanDF: DataFrame = ReadDataFrame.JSON(fullFileLoansURI)
    //val YTT:DataFrame = ReadDataFrame.hive("Select * from abc.test")

    //val YTT:DataFrame = ReadDataFrame.hive("Select * from abc.test")

    val (finDF, finDF1) = runELT(CustomersDF, LoanDF)

    (finDF, finDF1)
  }

  def runELT(CustomersDF: DataFrame, LoanDF: DataFrame): (DataFrame, DataFrame) = {

    val finDF = CustomersDF.join(LoanDF, CustomersDF("CustomerID") === LoanDF("CustomerID"))

    //finDF.createOrReplaceTempView("ANMF_BuyToLet_TBL")
    val ANMF_BtoLetDF = finDF.drop(CustomersDF("CustomerID")).repartition(2)
    val ANMF_BtoLetCust = ANMF_BtoLetDF.groupBy(ANMF_BtoLetDF("DOB")).count()
    //.agg(avg(empDf("salary")), max(empDf("age")))
    (ANMF_BtoLetDF, ANMF_BtoLetCust)
  }

}