package rule

import scala.collection.mutable

object Operator {


  val operatorsMap: Map[String, Map[String, Any]] = Map(

    "allOf" ->
      Map("precedence" -> 5, "symbols" -> List("AllOf", "allOf", "allof"), "operandCount" -> 3),
    "allof" ->
      Map("precedence" -> 5, "symbols" -> List("AllOf", "allOf", "allof"), "operandCount" -> 3),
    "AllOf" ->
      Map("precedence" -> 5, "symbols" -> List("AllOf", "allOf", "allof"), "operandCount" -> 3),

    "Between" ->
      Map("precedence" -> 5, "symbols" -> List("Between", "B/W", "BETWEEN", "between"), "operandCount" -> 3),
    "B/W" ->
      Map("precedence" -> 5, "symbols" -> List("Between", "B/W", "BETWEEN", "between"), "operandCount" -> 3),
    "BETWEEN" ->
      Map("precedence" -> 5, "symbols" -> List("Between", "B/W", "BETWEEN", "between"), "operandCount" -> 3),
    "between" ->
      Map("precedence" -> 5, "symbols" -> List("Between", "B/W", "BETWEEN", "between"), "operandCount" -> 3),

    "==" ->
      Map("precedence" -> 4, "symbols" -> List("=="), "operandCount" -> 2),

    ">" ->
      Map("precedence" -> 4, "symbols" -> List(">"), "operandCount" -> 2),

    ">=" ->
      Map("precedence" -> 4, "symbols" -> List(">="), "operandCount" -> 2),

    "<" ->
      Map("precedence" -> 4, "symbols" -> List("<"), "operandCount" -> 2),

    "<=" ->
      Map("precedence" -> 4, "symbols" -> List(">="), "operandCount" -> 2),

    "AND" ->
      Map("precedence" -> 2, "symbols" -> List("AND", "&&", "and"), "operandCount" -> 2),
    "&&" ->
      Map("precedence" -> 2, "symbols" -> List("AND", "&&", "and"), "operandCount" -> 2),
    "and" ->
      Map("precedence" -> 2, "symbols" -> List("AND", "&&", "and"), "operandCount" -> 2),

    "NOT" ->
      Map("precedence" -> 3, "symbols" -> List("NOT", "!", "not"), "operandCount" -> 1),
    "!" ->
      Map("precedence" -> 3, "symbols" -> List("NOT", "!", "not"), "operandCount" -> 1),
    "not" ->
      Map("precedence" -> 3, "symbols" -> List("NOT", "!", "not"), "operandCount" -> 1),

    "||" ->
      Map("precedence" -> 1, "symbols" -> List("||", "OR", "or"), "operandCount" -> 2),
    "OR" ->
      Map("precedence" -> 1, "symbols" -> List("||", "OR", "or"), "operandCount" -> 2),
    "or" ->
      Map("precedence" -> 1, "symbols" -> List("||", "OR", "or"), "operandCount" -> 2),

    "NoneOf" ->
      Map("precedence" -> 5, "symbols" -> List("NoneOf", "noneof", "noneOf"), "operandCount" -> 3),
    "noneof" ->
      Map("precedence" -> 5, "symbols" -> List("NoneOf", "noneof", "noneOf"), "operandCount" -> 3),
    "noneOf" ->
      Map("precedence" -> 5, "symbols" -> List("NoneOf", "noneof", "noneOf"), "operandCount" -> 3),

    "!=" ->
      Map("precedence" -> 4, "symbols" -> List("!=", "ne"), "operandCount" -> 2),
    "ne" ->
      Map("precedence" -> 4, "symbols" -> List("!=", "ne"), "operandCount" -> 2)

  )


  def operate(operator: String, operands: List[Any]): Boolean = {

    operator match {
      case "==" => {
        val operand1 = operands(0)
        val operand2 = operands(1)
        if (operand1.isInstanceOf[String] && operand2.isInstanceOf[String]) return operand1.asInstanceOf[String].equalsIgnoreCase(operand2.asInstanceOf[String])
        return operand1 == operand2
      }

      case "OR" | "or" | "||" => {
        for (operand <- operands) {
          if (operand.asInstanceOf[Boolean] == true) return true
        }
        return false
      }

      case "BETWEEN" | "between" | "B/W" | "Between" => {
        val operand = operands(0)
        val start = operands(1)
        val end = operands(2)
        var result: Boolean = true

        if (operand.isInstanceOf[Integer]) {
          result = ((operand.asInstanceOf[Integer] >= start.asInstanceOf[Integer]) && (operand.asInstanceOf[Integer] <= end.toString.toInt))
        }
        if (operand.isInstanceOf[Double]) {
          result = ((operand.asInstanceOf[Double] >= start.asInstanceOf[Double]) && (operand.asInstanceOf[Double] <= end.toString.toDouble))
        }
        result
      }

      case "AND" | "&&" | "and" => {
        for (operand <- operands) {
          if (operand.asInstanceOf[Boolean] == false) return false
        }
        return true
      }

      case ">" => {
        val operand1 = operands(0)
        val operand2 = operands(1)
        var result = true
        if (operand1.isInstanceOf[Integer]) {
          if (operand2.isInstanceOf[Integer]) result = operand1.asInstanceOf[Integer] > operand2.toString.toInt
          if (operand2.isInstanceOf[Double]) result = operand1.asInstanceOf[Integer] > operand2.toString.toDouble
        }
        if (operand1.isInstanceOf[Double]) {
          if (operand2.isInstanceOf[Double]) result = operand1.asInstanceOf[Double] > operand2.toString.toDouble
          if (operand2.isInstanceOf[Integer]) result = operand1.asInstanceOf[Double] > operand2.toString.toInt
        }
        result
      }

      case ">=" => {
        val operand1 = operands(0)
        val operand2 = operands(1)
        var result = true
        if (operand1.isInstanceOf[Integer]) {
          if (operand2.isInstanceOf[Integer]) result = operand1.asInstanceOf[Integer] >= operand2.toString.toInt
          if (operand2.isInstanceOf[Double]) result = operand1.asInstanceOf[Integer] >= operand2.toString.toDouble
        }
        if (operand1.isInstanceOf[Double]) {
          if (operand2.isInstanceOf[Double]) result = operand1.asInstanceOf[Double] >= operand2.toString.toDouble
          if (operand2.isInstanceOf[Integer]) result = operand1.asInstanceOf[Double] >= operand2.toString.toInt
        }
        result
      }

      case "<" => {
        val operand1 = operands(0)
        val operand2 = operands(1)
        var result = true
        if (operand1.isInstanceOf[Integer]) {
          if (operand2.isInstanceOf[Integer]) result = operand1.asInstanceOf[Integer] < operand2.toString.toInt
          if (operand2.isInstanceOf[Double]) result = operand1.asInstanceOf[Integer] < operand2.toString.toDouble
        }
        if (operand1.isInstanceOf[Double]) {
          if (operand2.isInstanceOf[Double]) result = operand1.asInstanceOf[Double] < operand2.toString.toDouble
          if (operand2.isInstanceOf[Integer]) result = operand1.asInstanceOf[Double] < operand2.toString.toInt
        }
        result

      }

      case "<=" => {
        val operand1 = operands(0)
        val operand2 = operands(1)
        var result = true
        if (operand1.isInstanceOf[Integer]) {
          if (operand2.isInstanceOf[Integer]) result = operand1.asInstanceOf[Integer] <= operand2.toString.toInt
          if (operand2.isInstanceOf[Double]) result = operand1.asInstanceOf[Integer] <= operand2.toString.toDouble
        }
        if (operand1.isInstanceOf[Double]) {
          if (operand2.isInstanceOf[Double]) result = operand1.asInstanceOf[Double] <= operand2.toString.toDouble
          if (operand2.isInstanceOf[Integer]) result = operand1.asInstanceOf[Double] <= operand2.toString.toInt
        }
        result
      }

      case "NOT" | "!" | "not" => {
        val operand = operands(0)
        (!operand.asInstanceOf[Boolean])
      }

      case "NoneOf" | "noneof" | "noneOf" => {
        val operand = operands(0)
        var operandList = List[Any]()
        for(i <- 1 until operands.size){
          operandList = operand :: operandList
          operandList = operands(i) :: operandList
          if (operate("==", operandList)) return false
        }
        return true
      }

      case "!=" | "ne" => {
        if (operands(0).isInstanceOf[String] && operands(1).isInstanceOf[String])
          return !(operands(0).asInstanceOf[String]).equalsIgnoreCase(operands(1).asInstanceOf[String])
        return !(operands(0) == operands(1))
      }
    }
  }
}
