  /****************************************************************************
            Author : 	Seshi

     Test POC - This is the main Driver class
      executed with Spark submit in yarn cluster
    This console app is purely written using dataframes
    *****************************************************************************/

  package com.sesh.bda.scala.train.BuyToLet

    import com.sesh.bda.scala.common.SparkCommonUtils.sqlContext
    import org.apache.spark.sql.{DataFrame, SparkSession}

    object BuytoLet_ANMF_Customer_Loans_App  {

      def main(args: Array[String]): Unit = {

        import com.sesh.bda.scala.common._
        import org.apache.spark.sql.functions._
        import org.apache.log4j.Logger
        import org.apache.log4j.Level

        Logger.getLogger("org").setLevel(Level.ERROR)
        Logger.getLogger("akka").setLevel(Level.ERROR)

        val spContext = SparkCommonUtils.spContext

        //This is taken as the argument to the Spark console App
        //For now, it'll be hardcoded

        val part_date:String="2017-01-03"

        val datadir : String = SparkCommonUtils.datadir+"ANMF_BuytoLet_" + part_date

        println("The spark version used here are " + spContext.version)

        println(" The prod version can be updated as required in the POM file - can set to 1.5.2")

        val (finDF:DataFrame,finDF1:DataFrame)  = ANMFBuyToLet.runPipeline(part_date)


        finDF.show()
        finDF1.show()

        Sink.parquetForm(finDF,datadir+"_Loans")
        Sink.parquetForm(finDF1,datadir+"_Customer") ;

        finDF.printSchema()
        finDF1.printSchema()

        val parq = sqlContext.read.parquet(datadir+"_Loans")
        val parq1 = sqlContext.read.parquet(datadir+"_Customer")
        parq.registerTempTable("ANMF_Loans")
        parq1.registerTempTable("ANMF_customers")
        sqlContext.sql("Select * FROM ANMF_Loans").show()
        sqlContext.sql("Select * FROM ANMF_customers").show()

      }
    }




