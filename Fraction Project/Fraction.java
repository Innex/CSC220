/*************************************************************************************
 *
 * This class represents a fraction whose numerator and denominator are integers.
 *
 * Requirements:
 *
 *      Implement interfaces: FractionInterface and Comparable (i.e. compareTo())
 *      Implement methods equals() and toString() from class Object
 *
 *      Should work for both positive and negative fractions
 *      Must always reduce fraction to lowest term 
 *      For number such as 3/-10, it is same as -3/10 (see hints 2. below)
 *      Must display negative fraction as -x/y, 
 *         example: (-3)/10 or 3/(-10), must display as -3/10
 *      Must throw excpetion in case of errors
 *      Must not add new or modify existing data fields
 *      Must not add new public methods
 *      May add private methods
 *
 * Hints:
 *
 * 1. To reduce a fraction such as 4/8 to lowest terms, you need to divide both
 *    the numerator and the denominator by their greatest common denominator.
 *    The greatest common denominator of 4 and 8 is 4, so when you divide
 *    the numerator and denominator of 4/8 by 4, you get the fraction 1/2.
 *    The recursive algorithm which finds the greatest common denominator of
 *    two positive integers is implemnted (see code)
 *       
 * 2. It will be easier to determine the correct sign of a fraction if you force
 *    the fraction's denominator to be positive. However, your implementation must 
 *    handle negative denominators that the client might provide.
 *           
 * 3. You need to downcast reference parameter FractionInterface to Fraction if  
 *    you want to use it as Fraction. See add, subtract, multiply and divide methods
 *
 * 4. Use "this" to access this object if it is needed
 *
 ************************************************************************************/
//Hossein Niazmandi
//CS 220
//10/09/2013
//Project 1
//913064794

package PJ1;

public class Fraction implements FractionInterface, Comparable<Fraction>
{
	private	int numerator;	
	private	int denominator;	

	public Fraction()
	{
		// set fraction to default 0, i.e. 0/1
		setFraction(0, 1);
	}	// end default constructor

	public Fraction(int iNumerator, int iDenominator)
	{
		// implement this method!
		numerator = iNumerator;
		denominator = iDenominator;
		
		//Make sure no zero Denominator, clear out exception early
		if(denominator ==0)
		{
			Exception E = new ArithmeticException("Can not have zero Denominator, Fraction default to 0/1");
			System.out.println(E);
			numerator=0;
			denominator=1;
		}
		
		//Move negative sign from denom to numerator
		if(denominator<0 && numerator<0)
		{
			denominator = denominator * -1;
			numerator = numerator * -1;
		}
		else if(denominator<0)
		{
			denominator = denominator * -1;
			numerator = numerator * -1;
		}
		
		this.reduceToLowestTerms();
		
	}	// end constructor

	public void setFraction(int nNumerator, int nDenominator)
	{
		// return ArithmeticException if initialDenominator is 0
		// implement this method!
		
		numerator = nNumerator;
		denominator = nDenominator;
		
		//Make sure no Zero Denom
		if(denominator==0)
		{
			Exception E = new ArithmeticException("Can not have zero Denominator, Fraction default to 0/1");
			System.out.println(E);
			numerator = 0;
			denominator = 1;
		}
		// place negative sign in numerator
		if(denominator < 0 && numerator < 0)
		{
			denominator = denominator * -1;
			numerator = numerator * -1;
		}
		else if(denominator < 0){
			denominator = denominator * -1;
			numerator = numerator * -1;
		}
		this.reduceToLowestTerms();
		
		
	}	// end setFraction

	public final int getNumerator()
	{
		// implement this method!
		return numerator;
	}	// end getNumerator

	public final int getDenominator()
	{
		// implement this method!
		return denominator;
	}	// end getDenominator

	public final char getSign()
	{
		// implement this method!
		if(numerator < 0 && denominator < 0)
			return '+';
		else if(numerator > 0 && denominator < 0)
			return '-';
		else if(numerator < 0 && denominator > 0)
			return '-';
		else
			return '+';
	}	// end getSign

	public void switchSign()
	{
		// implement this method!
		this.setFraction(this.getNumerator() * -1, this.getDenominator());
	}	// end switchSign

	public FractionInterface add(FractionInterface operand)
	{
		// a/b + c/d is (ad + cb)/(bd)
		// implement this method!
		Fraction F1 = (Fraction)this;
		Fraction F2 = (Fraction)operand;
		
		int nu = (F1.numerator * F2.denominator) + (F2.numerator * F1.denominator);
		int de = (F1.denominator * F2.denominator);
		
		Fraction F3 = new Fraction(nu, de);
		
		F3.reduceToLowestTerms();
		
		return (FractionInterface)F3;
			}	// end add

	public FractionInterface subtract(FractionInterface operand)
	{
		// a/b - c/d is (ad - cb)/(bd)
		// implement this method!
		Fraction F1 = (Fraction)this;
		Fraction F2 = (Fraction)operand;
		
		int nu = (F1.numerator * F2.denominator) - (F2.numerator * F1.denominator);
		int de = F1.denominator * F2.denominator;
		
		Fraction F3 = new Fraction(nu,de);
		F3.reduceToLowestTerms();
		
		return (FractionInterface)F3;
	}	// end subtract

	public FractionInterface multiply(FractionInterface multiplier)
	{
		// a/b * c/d is (ac)/(bd)
		// implement this method!
		Fraction F1 = (Fraction)this;
		Fraction F2 = (Fraction)multiplier;
		
		int nu = F1.numerator * F2.numerator;
		int de = F1.denominator * F2.denominator;
		
		Fraction F3 = new Fraction(nu,de);
		F3.reduceToLowestTerms();
		
		return (FractionInterface)F3;
	}	// end multiply


	public FractionInterface divide(FractionInterface divisor)
	{
		// return ArithmeticException if divisor is 0
		// a/b / c/d is (ad)/(bc)
		// implement this method!
		Fraction F1 = (Fraction)this;
		Fraction F2 = (Fraction)divisor;
		
		int nu = F1.numerator * F2.denominator;
		int de = F1.denominator * F2.numerator;
		
		Fraction F3 = new Fraction(nu,de);
		F3.reduceToLowestTerms();
		
		return (FractionInterface)F3;
	}	// end divide

	public FractionInterface getReciprocal() 
	{
		// return ArithmeticException if secondFraction is 0
		// implement this method!
		Fraction temp = new Fraction(this.denominator,this.numerator);
		
		return(FractionInterface)temp;
		
	} // end getReciprocal


	public boolean equals(Object other)
	{
		Fraction F1=(Fraction)other;
		if(this.numerator == F1.numerator && this.denominator == F1.denominator)
		return true;
		return false;
		
	}


	public int compareTo(Fraction other)
	{
	double F1 = (double)this.numerator/(double)this.denominator;
	double F2 = (double)other.numerator/(double)other.numerator;
		
		if(F1>F2)
			return 1;
		else if(F1<F2)
			return -1;
		return 0;
	} 
	//end compare

	
	public String toString()
	{
		return numerator + "/" + denominator;
	} // end toString


	/** Task: Reduces a fraction to lowest terms. */

        //-----------------------------------------------------------------
        //  private methods start here
        //-----------------------------------------------------------------

	private void reduceToLowestTerms()
	{
                // implement this method!
                //
                // Outline:
                // compute GCD of numerator & denominator
                // greatestCommonDivisor works for + numbers.
                // So, you should eliminate - sign
                // then reduce numbers : numerator/GCD and denominator/GCD
		// end reduceToLowestTerms

		int tempNu = this.numerator;
		if(tempNu < 0)
			tempNu = tempNu * -1;
			
		int GCD = this.greatestCommonDivisor(tempNu, this.denominator);
		
		this.numerator = this.numerator/GCD;
		this.denominator = this.denominator/GCD;
		
	} // End reduce.
	
		
  	/** Task: Computes the greatest common secondFraction of two integers.
	 *  @param integerOne	 an integer
	 *  @param integerTwo	 another integer
	 *  @return the greatest common divisor of the two integers */
	private int greatestCommonDivisor(int integerOne, int integerTwo)
	{
		 int result;

		 if (integerOne % integerTwo == 0)
			result = integerTwo;
		 else
			result = greatestCommonDivisor(integerTwo, integerOne % integerTwo);

		 return result;
	}	// end greatestCommonDivisor


	//-----------------------------------------------------------------
	//  Simple test is provided here 

	public static void main(String[] args)
	{
		FractionInterface firstOperand = null;
		FractionInterface secondOperand = null;
		FractionInterface result = null;

		Fraction nineSixteenths = new Fraction(9, 16);	// 9/16
		Fraction oneFourth = new Fraction(1, 4);        // 1/4

		// 7/8 + 9/16
		firstOperand = new Fraction(7, 8);
		result = firstOperand.add(nineSixteenths);
		System.out.println("The sum of " + firstOperand + " and " +
				nineSixteenths + " is \t\t" + result);

		// 9/16 - 7/8
		firstOperand = nineSixteenths;
		secondOperand = new Fraction(7, 8);
		result = firstOperand.subtract(secondOperand);
		System.out.println("The difference of " + firstOperand	+
				" and " +	secondOperand + " is \t" + result);

		// 15/-2 * 1/4
		firstOperand.setFraction(15, -2);
		result = firstOperand.multiply(oneFourth);
		System.out.println("The product of " + firstOperand	+
				" and " +	oneFourth + " is \t" + result);

		// (-21/2) / (3/7)
		firstOperand.setFraction(-21, 2);
		secondOperand.setFraction(3, 7);
		result = firstOperand.divide(secondOperand);
		System.out.println("The quotient of " + firstOperand	+
				" and " +	secondOperand + " is \t" + result);

		// -21/2 + 7/8
		firstOperand.setFraction(-21, 2);
		secondOperand.setFraction(7, 8);
		result = firstOperand.add(secondOperand);
		System.out.println("The sum of " + firstOperand	+
				" and " +	secondOperand + " is \t\t" + result);

		System.out.println();

		// equality check
		if (firstOperand.equals(firstOperand))
			System.out.println("Identity of fractions OK");
		else
			System.out.println("ERROR in identity of fractions");

		secondOperand.setFraction(-42, 4);
		if (firstOperand.equals(secondOperand))
			System.out.println("Equality of fractions OK");
		else
			System.out.println("ERROR in equality of fractions");

		// comparison check
		Fraction first  = (Fraction)firstOperand;
		Fraction second = (Fraction)secondOperand;
		
		if (first.compareTo(second) == 0)
			System.out.println("Fractions == operator OK");
		else
			System.out.println("ERROR in fractions == operator");

		second.setFraction(7, 8);
		if (first.compareTo(second) < 0)
			System.out.println("Fractions < operator OK");
		else
			System.out.println("ERROR in fractions < operator");

		if (second.compareTo(first) > 0)
			System.out.println("Fractions > operator OK");
		else
			System.out.println("ERROR in fractions > operator");

		System.out.println();

		try {
			Fraction a1 = new Fraction(1, 0);		    
		}
		catch ( ArithmeticException arithmeticException )
           	{
              		System.err.printf( "\nException: %s\n", arithmeticException );
           	} // end catch

		try {
			Fraction a2 = new Fraction();		    
			Fraction a3 = new Fraction(1, 2);		    
			a3.divide(a2);
		}
		catch ( ArithmeticException arithmeticException )
           	{
              		System.err.printf( "\nException: %s\n", arithmeticException );
           	} // end catch



	}	// end main
} // end Fraction

