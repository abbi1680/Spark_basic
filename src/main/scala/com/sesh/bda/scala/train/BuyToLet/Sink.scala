package com.sesh.bda.scala.train.BuyToLet
import java.util.Calendar

import com.sesh.bda.scala.common.SparkCommonUtils
import com.sesh.bda.scala.common.SparkCommonUtils.sqlContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.Logger
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}

object Sink {

  def parquetForm(finDF:DataFrame,datadir:String): Unit = {

    finDF.write.parquet(datadir)

  }



}
