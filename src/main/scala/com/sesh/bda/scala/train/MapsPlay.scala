package com.sesh.bda.scala.train

/**
  * Created by sreej on 05/05/2017.
  */
object MapsPlay {
def main(args:Array[String]): Unit =
  {
    val nMap = Map("GenderQuery" -> "Select * from Genders" ,
                   "ProfQuery"   -> "Select * from Professions",
                  "OrdersQuery"  -> "Select * from Orders"
        )


    println(nMap("GenderQuery"))




  }
}


////Control Enter - IDEA