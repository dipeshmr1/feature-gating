# FeautureGating
A feature gating module has been designed to decide if the user is granted access to a particular feature or not. This is based on conditional expression which is evaluated against user attributes.
Furthermore, the module is easily extensible in terms of adding attributes and operators. The relative precedence, operand count, the symbols supported, operand types on which the operator is defined, and the operation logic can all be updated effortlessly. The operator can be any logical one with different numbers of operands required and different types of operands. The conditional expression supports any infix expression. The model supports the following operators: >, >=, <=, <, ==, !=, BETWEEN, ALLOF, NONEOF. 


####The following rules need to be followed while creating the conditional expression when creating a new feature gates in the code:
1. Whitespace to be used between each operand and operator and also before and after brackets. Eg.
2. Symbols for each operator can be found in that operator file and can be written in multiple ways.
eg. Between can be written B/W, BETWEEN, Between, between.
3. Supported data types are Integer, Double, String, Boolean
4. User attributes should be Map. eg. Map("Age" -> 30, "City" -> "Mumbai")

####Steps to check user accessibility:-
1. Define the user attribute in the MainProgram using Map eg.Map("Age" -> 30, "City" -> "Mumbai")
2. Pass the expression to check the accessibility of the feature. eg.RuleEngine.evaluateFeature("(age > 25 AND gender == Male) OR (past_order_amount > 10000)", userMap)
