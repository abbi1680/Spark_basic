/**
  * Created by sreej on 05/06/2017.
  */

case class Couple[A, B](first:A, second:B) {
  def swapNames:Couple[B, A] = new Couple(second, first)

}

object test5 extends App {




  val couple1 = Couple("Sesh","Reddy")
  val fullname = couple1.first + " " + couple1.second
//  val couple2 = Couple(2,1)
//  val couple3 = Couple(2,"Sesh")
println(fullname)

}
