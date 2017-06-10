package com.sesh.bda.scala.train.BuyToLet

import com.sesh.bda.scala.common.SparkCommonUtils._
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

trait BuildContext {


  def loadProperties(fileName: String): Map[String, String] = {
    spContext.textFile(fileName).map(x => {
      val y = x.split("=>")
      (y(0), y(1))
    }).collect.toMap
  }
}

/*
ANMFHiveQuery => SELECT Count(*) from ABC where date >="2017-01-01"
ANSSHiveQuery => SELECT Count(*) from EFF where date >="2017-01-01"
BDPHiveQuery => SELECT Count(*) from ABC where date >="2017-01-01"


 */