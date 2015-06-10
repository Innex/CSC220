/************************************************************************************
 *
 *  		CSC220 Programming Project#2
 *  
 * Due Date: 23:59pm, Monday, 11/04/2013  
 *           Upload LispExprEvaluator.java to ilearn 
 *
 * Specification: 
 *
 * Taken from Project 6, Chapter 5, Page 137
 * I have modified specification and requirements of this project
 *
 * Ref: http://www.gigamonkeys.com/book/        (see chap. 10)
 *      http://joeganley.com/code/jslisp.html   (GUI)
 *
 * In the language Lisp, each of the four basic arithmetic operators appears 
 * before an arbitrary number of operands, which are separated by spaces. 
 * The resulting expression is enclosed in parentheses. The operators behave 
 * as follows:
 *
 * (+ a b c ...) returns the sum of all the operands, and (+ a) returns a.
 *
 * (- a b c ...) returns a - b - c - ..., and (- a) returns -a. 
 *
 * (* a b c ...) returns the product of all the operands, and (* a) returns a.
 *
 * (/ a b c ...) returns a / b / c / ..., and (/ a) returns 1 / a. 
 *
 * Note: each operator must have at least one operand
 *
 * You can form larger arithmetic expressions by combining these basic 
 * expressions using a fully parenthesized prefix notation. 
 * For example, the following is a valid Lisp expression:
 *
 * 	(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)))
 *
 * This expression is evaluated successively as follows:
 *
 *	(+ (- 6) (* 2 3 4) (/ 3 1 -2))
 *	(+ -6 24 -1.5)
 *	16.5
 *
 * Requirements:
 *
 * - Design and implement an algorithm that uses Java API stacks to evaluate a 
 *   Valid Lisp expression composed of the four basic operators and integer values. 
 * - Valid tokens in an expression are '(',')','+','-','*','/',and positive integers (>=0)
 * - In case of errors, your program must throw LispExprEvaluatorException
 * - Display result as floting point number with at 2 decimal places
 * - Negative number is not a valid "input" operand, e.g. (+ -2 3) 
 *   However, you may create a negative number using parentheses, e.g. (+ (-2)3)
 * - There may be any number of blank spaces, >= 0, in between tokens
 *   Thus, the following expressions are valid:
 *   	(+   (-6)3)
 *   	(/(+20 30))
 *
 * - Must use Java API Stack class in this project.
 *   Ref: http://docs.oracle.com/javase/7/docs/api/java/util/Stack.html
 * - Must throw LispExprEvaluatorException to indicate errors
 * - Must not add new or modify existing data fields
 * - Must implement these methods : 
 *
 *   	public LispExprEvaluator()
 *   	public LispExprEvaluator(String inputExpression) 
 *      public void reset(String inputExpression) 
 *      public double evaluate()
 *      private void evaluateCurrentOperation()
 *
 * - You may add new private methods
 *
 *************************************************************************************/
/*Hossein Niazmandi
 * CSC 220
 * Fall 2013
 * 913064794
 */
package PJ2;
import java.util.*;

public class LispExprEvaluator
{
    // Current input Lisp expression
    private String inputExpr;

    // Main expression stack & current operation stack, see algorithm in evaluate()
    private Stack<Object> tExprStack;
    private Stack<Double> tOpStack;

    	// default constructor
    	// set inputExpr to "" 
    	// create stack objects
    
  
    public LispExprEvaluator()
    {
    	inputExpr="";
		tExprStack = new Stack<Object>();
		tOpStack = new Stack<Double>();
	
    }

    	// constructor with an input expression 
    	// set inputExpr to inputExpression 
    	// create stack objects
    public LispExprEvaluator(String inputExpression) 
    {
    	// add statements
    	inputExpr = inputExpression;
    	tExprStack = new Stack<Object>();
    	tOpStack = new Stack<Double>();
    	
    }

    // set inputExpr to inputExpression 
    // clear stack objects
    public void reset(String inputExpression) 
    {
    	
	// add statements
    	inputExpr = inputExpression;
    	tExprStack.clear();
    	tOpStack.clear();
    
    }


    // This function evaluates current operator with its operands
    // See complete algorithm in evaluate()
    //
    // Main Steps:
    // 		Pop operands from thisExprStack and push them onto 
    // 			thisOpStack until you find an operator
    //  	Apply the operator to the operands on thisOpStack
    //          Push the result into thisExprStack
    //
  
    private void evaluateCurrentOperation()
    {    
        char operator = ' ';
        double result = 0.0;

        //while thisExprStack is not empty and top of the stack is not an
        //operator, pop the operand from thisExprStack and push it onto thisOpStack
        while (!tExprStack.isEmpty()){    
              
            String token = tExprStack.pop().toString();               
            
            //if the token can't be turned into double, we have a character 
            // ---> catch exception and set the token to be operator
            try {
                double operand = Double.parseDouble(token);  
                tOpStack.push(operand);    
            }
            catch(IllegalArgumentException e){
                operator = token.charAt(0);    
                break;
            }    
        }    
  
            //calculate the result according to the operator
            //and push it onto thisExprStack
            switch (operator){

            //in no operator is found, throw an exception
            case ' ':
                throw new LispExprEvaluatorException("missing an operator");
            
            case '+':
                while (!tOpStack.isEmpty()){
                    double variable = tOpStack.pop();
                    result += variable;
                }
                tExprStack.push(result);
                break;
             
            case '-':
                //if there's only one operand, result
                //will be simply 0 - value of the operand
                if(tOpStack.size() <= 1){
                    double variable = tOpStack.pop();
                    result -= variable;
                }
                else { 
                    result = tOpStack.pop();
                    while (!tOpStack.isEmpty()){
                        double variable = tOpStack.pop();
                        result -= variable;
                    }
                }
                tExprStack.push(result);
                break;
            
            case '*':
                result = tOpStack.pop();
                while (!tOpStack.isEmpty()){
                    double variable = tOpStack.pop();
                    result *= variable;
                }
                tExprStack.push(result);
                break;
                
            case '/':
                //if there's only one operand, the result
                //will be 1/operand
                if(tOpStack.size() <= 1){
                    double variable = tOpStack.pop();
                    result = 1/variable;
                }
                //if there's more than one operand, keep dividing
                //the operands unless ArithmeticException occurs
                else {
                    result = tOpStack.pop();
                    while (!tOpStack.isEmpty()){        
                        double variable = tOpStack.pop();
                        
                        if (variable == 0){
                            throw new ArithmeticException("ERROR: can't divide by zero");
                        }
                        else {
                            result /= variable;
                        }
                    }
                }    
                tExprStack.push(result);
                break;        
        }
    }
    
    /**
     * This funtion evaluates current Lisp expression in inputExpr
     * It return result of the expression 
     *
     * The algorithm:  
     *
     * Step 1   Scan the tokens in the string.
     * Step 2		If you see an operand, push operand object onto the thisExprStack
     * Step 3  	    	If you see "(", next token should be an operator
     * Step 4  		If you see an operator, push operator object onto the thisExprStack
     * Step 5		If you see ")"  // steps in evaluateCurrentOperation() :
     * Step 6			Pop operands and push them onto thisOpStack 
     * 					until you find an operator
     * Step 7			Apply the operator to the operands on thisOpStack
     * Step 8			Push the result into thisExprStack
     * Step 9    If you run out of tokens, the value on the top of thisExprStack is
     *           is the result of the expression.
     */
    public double evaluate()
    {
        // only outline is given...
        // you need to add statements/local variables
        // you may delete or modify any statements in this method

        // use scanner to tokenize inputExpr
         Scanner inputExprScanner = new Scanner(inputExpr);
        
        // Use zero or more white space as delimiter,
        // which breaks the string into single character tokens
        inputExprScanner = inputExprScanner.useDelimiter("\\s*");

        // Step 1: Scan the tokens in the string.
        while (inputExprScanner.hasNext())
        {
		
     	    // Step 2: If you see an operand, push operand object onto the thisExprStack
            if (inputExprScanner.hasNextInt())
            {
                // This force scanner to grab all of the digits
                // Otherwise, it will just get one char
                String dataString = inputExprScanner.findInLine("\\d+");

   		// more ...
                tExprStack.push(dataString);
            }
            else
            {
            	// Get next token, only one char in string token
                String aToken = inputExprScanner.next();
                char item = aToken.charAt(0);
                
                switch (item)
                {
                // Step 3: If you see "(", next token should an operator
                case '(':
            	break;
                    
            	// Step 4: If you see an operator, push operator object onto the exprStack	
            	case '+': case '-': case '*': case '/':
            		tExprStack.push(item);
            	break;
        
            	// Step 5: If you see ")"  do steps 6,7,8 in evaluateCurrentOperation()
            	case ')':
            		evaluateCurrentOperation();
                break;
            	
                default:  // error
                        throw new LispExprEvaluatorException(item + " Not Legal expression Op.");
                } // end switch
            } // end else
        } // end while
        // Step 9: If you run out of tokens, the value on the top of exprStack is
        //         is the result of the expression.
        //         return result
        if (tExprStack.size() > 1){
            throw new LispExprEvaluatorException("illegal expression");
        }  
        String topValue = tExprStack.peek().toString();               
        return Double.parseDouble(topValue);  

    }
    

    //=====================================================================
    // DO NOT MODIFY ANY STATEMENTS BELOW
    //=====================================================================

    // This static method is used by main() only
    private static void evaluateExprTest(String s, LispExprEvaluator expr)
    {
        Double result;
        System.out.println("Expression " + s);
	expr.reset(s);
        result = expr.evaluate();
        System.out.printf("Result %.2f\n", result);
        System.out.println("-----------------------------");
    }

    // define few test cases, exception may happen
    public static void main (String args[])
    {
        LispExprEvaluator expr= new LispExprEvaluator();
        String test1 = "(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)))";
        String test2 = "(+ (- 632) (* 21 3 4) (/ (+ 32) (* 1) (- 21 3 1)))";
        String test3 = "(+ (/ 2) (* 2) (/ (+ 1) (+ 1) (- 2 1 )))";
        String test4 = "(+ (/2))";
        String test5 = "(+ (/2 3 0))";
        String test6 = "(+ (/ 2) (* 2) (/ (+ 1) (+ 3) (- 2 1 ))))";
	evaluateExprTest(test1, expr);
	evaluateExprTest(test2, expr);
	evaluateExprTest(test3, expr);
	evaluateExprTest(test4, expr);
	evaluateExprTest(test5, expr);
	evaluateExprTest(test6, expr);
    }
}
