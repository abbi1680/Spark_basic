package com.sesh.bda.scala.train

import java.util.Calendar

import com.sesh.bda.scala.common.SparkCommonUtils
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object SparkDriver
  {
def main(args:Array[String]): Unit =
  {

   import com.sesh.bda.scala.common._
    import org.apache.spark.sql.functions._
    import org.apache.log4j.Logger
    import org.apache.log4j.Level;

    Logger.getLogger("org").setLevel(Level.ERROR);
    Logger.getLogger("akka").setLevel(Level.ERROR);

   // val spSession = SparkCommonUtils.spSession
    val spContext = SparkCommonUtils.spContext
    val datadir = SparkCommonUtils.datadir
    val spSession=SparkCommonUtils.spSession
    val customerJson=datadir + "customerData.json"
    val deptJson=datadir + "department.json"

    println("Customer.process is invoked at time  ? ")

    /*-------------------------------------------------------------------
   * Problem Decomposition
   -------------------------------------------------------------------*/


    /*

    Read Properties File

    lazy val properties  : Map[String,String] = loadProperties(args(0), sparkContext)

     */
    val (aggDF:DataFrame,avgDF:DataFrame) = Customer_v1.process(spSession,customerJson,deptJson)

    println("Customer.process is completed at time ? " )

    aggDF.show()
    avgDF.show()





  }


}


// Control Shift R

object Customer_v1 {

  def process(spSession: SparkSession,customerJson:String,deptJson:String) =
  {
    val customerDF = spSession.read.json(customerJson)
    val deptDF = spSession.read.json(deptJson)

   val aggDeptCountDF =  customerDF.filter(customerDF("age") > 30).
      join(deptDF,customerDF("deptid") === deptDF("deptid"))
      .groupBy(customerDF("deptid")).count()

    val maxDeptAgeDF =  customerDF.filter(customerDF("age") > 30).
      join(deptDF,customerDF("deptid") === deptDF("deptid"))
      .groupBy(customerDF("deptid")).max("age")

    (aggDeptCountDF,maxDeptAgeDF)


  }
}
