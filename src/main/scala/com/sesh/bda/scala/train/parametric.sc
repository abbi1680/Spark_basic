case class Couple[A, B](first:A, second:B) {
  def swapNames:Couple[B, A] = new Couple(second, first)

}

val couple1 = Couple("Sesh","Reddy")
val couple2 = Couple(2,1)
val couple3 = Couple(2,"Sesh")

val fullname = couple1.first + " " + couple1.second


couple1.swapNames("redy","sesh")

