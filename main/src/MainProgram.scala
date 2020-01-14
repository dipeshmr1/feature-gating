import rule.RuleEngine

object MainProgram {


  def main(args: Array[String]): Unit = {
    val userMap = Map("age" -> 30, "gender" -> "Male", "totalOrderAmount" -> 20000)

    if (RuleEngine.evaluateFeature("( age > 25 AND gender == Male ) OR ( past_order_amount > 10000 )", userMap)) {
      println("User is allowed to access this feature")

    } else {
      println("User is not allowed to access this feature")

    }
  }
}
