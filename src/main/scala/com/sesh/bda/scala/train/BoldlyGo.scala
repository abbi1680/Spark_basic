package com.sesh.bda.scala.train

object BoldlyGo extends App {
  val explorer = new Explorer
  println(explorer)

  abstract class Spacecraft { def engage(): Unit }

  trait Bridge {
    def speedUp(): Unit
    def engage(): Unit = { speedUp(); speedUp(); speedUp() }
  }

  trait Engine { def speedUp(): Unit }

  trait PulseEngine extends Engine {
    var currentPulse = 0
    def maxPulse: Int
    def speedUp(): Unit = if (currentPulse < maxPulse) currentPulse += 1
  }

  trait ControlCabin {
    def increaseSpeed(): Unit
    def engage(): Unit = increaseSpeed()
  }

  class Shuttle extends Spacecraft with ControlCabin with PulseEngine {
    val maxPulse = 10
    def increaseSpeed(): Unit = speedUp()
  }

  trait WarpEngine extends Engine {
    def maxWarp: Int
    var currentWarp: Int = 0
    def toWarp(x: Int): Unit = if (x < maxWarp) currentWarp = x
  }

  class Explorer extends Spacecraft with Bridge with WarpEngine {
    val maxWarp = 10
    def speedUp(): Unit = toWarp(currentWarp + 1)
  }

  object Defiant extends Spacecraft with ControlCabin with WarpEngine {
    val maxWarp = 20
    def increaseSpeed() = toWarp(10)
    def speedUp(): Unit = toWarp(currentWarp + 2)
  }
}