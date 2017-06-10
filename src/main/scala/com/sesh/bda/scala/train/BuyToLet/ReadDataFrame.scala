
/** **************************************************************************
  * *
  * Author : 					Seshi
  * *
  * Test POC - This is used to read Dataframes and have every format
  *
  * ****************************************************************************/

package com.sesh.bda.scala.train.BuyToLet

import java.util.Calendar

import com.sesh.bda.scala.common.SparkCommonUtils
import com.sesh.bda.scala.common.SparkCommonUtils.sqlContext
import com.sun.javafx.util.Logging
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.Logger
import org.apache.spark.sql.{DataFrame, SparkSession}


object ReadDataFrame {

  def JSON(tableName: String): DataFrame = {

    val df = sqlContext.read.json(tableName)

    df
  }


  def parquetForm(tableName: String, part_date: String): DataFrame = {

    val df = sqlContext.read.parquet(tableName)

    df
  }

  def hive(SQLQuery: String): DataFrame = {

    val df = sqlContext.sql(SQLQuery)

    df
  }

}
