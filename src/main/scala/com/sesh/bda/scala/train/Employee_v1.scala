package com.sesh.bda.scala.train
import java.time._
/**
  * Created by sreej on 05/05/2017.
  */
class Employee_v1 private (val firstName:String, val lastName:String,val title:String,val hireDate:LocalDate) {


}

object Employee_v1 {

  def apply( firstName:String, lastName:String,title:String) = new Employee_v1(firstName,lastName,title,LocalDate.now())

}

object EmployeeRunner_v1 extends App
{

  //val  sesh = new Employee("Sesh","Redd","Mr",LocalDate.now())

  // println(sesh.firstName)

  val newEmp =   Employee_v1("Sesh","Redd","Mr")

  println(newEmp.hireDate)

}