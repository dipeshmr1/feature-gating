package rule

import java.util

import scala.collection.mutable

object RuleEngine {

  val operatorsMap = Operator.operatorsMap

  def getPostfix(exp: String): String = {
    var result = new String("")
    val stack = new util.Stack[String]
    val elements = exp.split(" ")
    for (element <- elements) {
      if (element.equalsIgnoreCase("(")) stack.push(element)
      else if (element.equalsIgnoreCase(")")) {
        while (!stack.isEmpty && !stack.peek.equalsIgnoreCase("(")) result = result + stack.pop + " "
        if (!stack.isEmpty && !stack.peek.equalsIgnoreCase("(")) return "Invalid Expression"
        else stack.pop
      }
      else if (!isOperator(element)) result = result + element + " "
      else {
        while (!stack.isEmpty && getPrecedence(element) <= getPrecedence(stack.peek)) {
          if (stack.peek.equalsIgnoreCase("(")) return "Invalid Expression"
          result = result + stack.pop + " "
        }
        stack.push(element)
      }
    }
    while (!stack.isEmpty) {
      if (stack.peek.equalsIgnoreCase("(")) return "Invalid Expression"
      result = result + stack.pop + " "
    }
    result
  }

  def isOperator(str: String): Boolean = {
    for (key <- operatorsMap.keySet) {
      if (str.equalsIgnoreCase(key)) return true
    }
    false
  }

  def getPrecedence(str: String) = if (operatorsMap.contains(str)) operatorsMap(str)("precedence").toString.toInt
  else -1

  def evaluatePostfix(postfixExpression: String, userProperty: Map[String, Any]): Boolean = {
    val stack = mutable.Stack[Any]()
    val elements = postfixExpression.split(" ")
    for (element <- elements) {
      if (isOperator(element)) {
        var operandList = List[Any]()

        for (i <- 1 to operatorsMap(element)("operandCount").toString.toInt) {
          operandList = (stack.pop) :: operandList

        }
        operandList = operandList
        stack.push(Operator.operate(element, operandList))
      }
      else {
        val value = userProperty.get(element)

        if (value != None) stack.push(value.get)
        else if (element.equalsIgnoreCase("true")) stack.push(true)
        else if (element.equalsIgnoreCase("false")) stack.push(false)
        else {
          try {
            val elementValue = element.toInt
            stack.push(elementValue)

          } catch {
            case ex: Exception => {
              try {
                val elementValue = element.toDouble
                stack.push(elementValue)
              } catch {
                case ex: Exception => {
                  stack.push(element)

                }
              }
            }
          }

        }
      }
    }
    stack.pop.asInstanceOf[Boolean]
  }

  def evaluateFeature(expression: String, userMap: Map[String, Any]): Boolean ={
    evaluatePostfix(getPostfix(expression).replace("(", "").replace(")", ""), userMap)
  }

}
