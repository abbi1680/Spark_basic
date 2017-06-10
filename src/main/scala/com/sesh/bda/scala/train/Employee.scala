package com.sesh.bda.scala.train
import java.time._
/**
  * Created by sreej on 05/05/2017.
  */
class Employee private (val firstName:String, val lastName:String,val title:String,val hireDate:LocalDate) {



}



object Employee {

  def create( firstName:String, lastName:String,title:String) = new Employee(firstName,lastName,title,LocalDate.now())

}

object EmployeeRunner extends App
{

  //val  sesh = new Employee("Sesh","Redd","Mr",LocalDate.now())

 // println(sesh.firstName)

  val newEmp =   Employee.create("Sesh","Redd","Mr")

  println(newEmp.hireDate)

}