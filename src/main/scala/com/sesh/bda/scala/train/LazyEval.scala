
import java.util.Calendar
object LazyEval {
  def main(args: Array[String]): Unit = {



    /** The Leibniz series algorithm converges slowly to Pi. About 5 billion iterations are required to yield accuracy to 10 decimal places.
      * Pi is the limit of this series: 4/1 - 4/3 + 4/5 - 4/7 + 4/9 ...
      *
      * @see http://en.wikipedia.org/wiki/Leibniz_formula_for_%CF%80 */
    def leibnizPi(iterationCount: Long = 5000000000L, digits: Int = 10): BigDecimal = {
    val numerator: BigDecimal = 4.0
    var denominator: BigDecimal = 1
    var plus = true
    var result: BigDecimal = 0.0
    while (denominator < iterationCount) {
    if (plus) {
    result = result + numerator / denominator
    plus = false
  } else {
    result = result - numerator / denominator
    plus = true
  }
    denominator = denominator + 2
  }
    result.setScale (digits, BigDecimal.RoundingMode.HALF_UP)
  }

    val isWitchingHour: Boolean = Calendar.getInstance ().get (Calendar.HOUR_OF_DAY) == 0
    val scaredMsg = "I am too scared to compute"

    def timidPi1(value: BigDecimal): String = if (! isWitchingHour) s"Eager evaluation yields $value" else scaredMsg
    def timidPi2(value: => BigDecimal): String = if (! isWitchingHour) s"Lazy evaluation yields $value" else scaredMsg
    def timidPi3(value: () => BigDecimal): String = if (! isWitchingHour) s"Evaluating function yields ${value ()}" else scaredMsg

    println (timidPi1 (leibnizPi () ) ) // eager evaluation
    println (timidPi2 (leibnizPi () ) ) // lazy evaluation
    println (timidPi3 (() => leibnizPi () ) ) // no-arg function
  }
}