/*
 * Assignment 1 - Part 2 - Cracking the caesar cipher
 * Student - Ana Chachua
 */

public class Brutus
{
	public static final String alphabetLower = "abcdefghijklmnopqrstuvwxyz"; 
	public static final String alphabetUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
	
	public static final double[] english = {
			0.0855, 0.0160, 0.0316, 0.0387, 0.1210, 0.0218, 0.0209, 0.0496, 0.0733,
			0.0022, 0.0081, 0.0421, 0.0253, 0.0717, 0.0747, 0.0207, 0.0010, 0.0633,
			0.0673, 0.0894, 0.0268, 0.0106, 0.0183, 0.0019, 0.0172, 0.0011
			};
	/**
	 * main method that can be used to decipher Caesar-encoded English cryptotext without the key
	 * @param args - command line argument
	 */
	public static void main(String args[]) {
	    int len = args.length;
		if(len > 1)
		{
			System.out.println("Too many parameters!");
            System.out.println("Usage: java Brutus \"cipher text\" ");
			return;
		}
		
		if(len < 1)
		{
			System.out.println("Too few parameters!");
            System.out.println("Usage: java Brutus \"cipher text\" ");
			return;			
		}
		
		String cipherText = args[0]; 
		String shiftedText; 
		String decryptedText; //final result
		
		//initialise rotation amounts
		int shiftAmount = 1; 
		int actualAmount = 0; 
		double tempChiSq = 1000000; //initialise an unrealistically large chisquared score
		
		double[] frequencies = new double [26];
		double[] closeness = new double[25];
		
		//shift the cipher text by shiftAmount (1 at start)
		//calculate frequency of each letter
		//evaluate closeness using chisquared test
		//increment shiftAmount by 1
		while(shiftAmount < 26)
		{
			shiftedText = rotate(shiftAmount, cipherText);
			frequencies = frequency(shiftedText);
			closeness[shiftAmount - 1] = chiSquared(frequencies, english);
			
			shiftAmount++;
		}
		
		for(int i = 0; i < closeness.length; i++)
		{
			if(closeness[i] < tempChiSq)
			{
				actualAmount = i + 1;
				tempChiSq = closeness[i];
			}
		}
		
		//decrypt the text
		decryptedText = rotate(actualAmount, cipherText);
		System.out.println(decryptedText);
		
	}

	/**
	 * method called count that takes a single String parameter and returns a length-26 integer array
	 * @param phrase
	 * @return letterCounts
	 */
	public static int[] count(String phrase) {
	
		int i = 0;
		int diff;
		char letter;	
		
		int[] letterCounts = new int[26];
		phrase = phrase.toLowerCase(); //text is converted to lower case
		
		while(i < phrase.length())
		{
			letter = phrase.charAt(i);
	    	if(alphabetLower.contains(Character.toString(letter)))
	    	{    	
				diff = letter - 'a';
				letterCounts[diff] = letterCounts[diff] + 1; //use the difference as index and increment the value by 1
	    	}
			i++;
			
		}
	
		return letterCounts;
	}
	
	/**
	 * method called frequency that takes a single String and returns a length-26 array of doubles.
	 * @param phrase
	 * @return letterFreq
	 */
	public static double[] frequency(String phrase) {
		int i = 0;
		int[] letterCount = count(phrase);
		double[] letterFreq = new double[26];
		
		//temporary variables
		double tempCount;
		double tempLength;
		
		while(i < letterFreq.length)
		{
			tempCount = letterCount[i]; //get the frequency of the letter in the alphabet at the index i
			tempLength = phrase.length(); //get the length of the text
			letterFreq[i] = tempCount / tempLength;
			i++;
		}

		return letterFreq;
	}
	

    /**
	 * method called chiSquared, which returns the chisquared (a double) for two given sets of frequencies.
	 * @param letterF
	 * @param alphabetF
	 * @return chiSq
	 */

	public static double chiSquared(double[] letterF, double[] alphabetF) {
		int i = 0;
		double chiSq = 0;
		while(i < letterF.length)
		{
			chiSq += (Math.pow((letterF[i] - alphabetF[i]), 2))/alphabetF[i];
			i++;
		}
		
		return chiSq;
	}
	
	/**
	 * From Part 1 - rotates string;
	 * @param amount
	 * @param phrase
	 * @return rotatedPhrase
	 * 
	 */
	public static String rotate(int amount, String phrase) {
    
    	int i = 0;
    	char individual; //individual character
    	char rotatedChar; //rotated character
    	
    	String originalPhrase = phrase; //copy original phrase passed as argument
    	String rotatedPhrase = ""; //initialise rotated phrase
    	
    	int length = originalPhrase.length();
    	
    	while(i < length)
    	{
    		individual = originalPhrase.charAt(i);
    		rotatedChar = rotate(amount, individual); //rotate_char function call
    		rotatedPhrase = rotatedPhrase + rotatedChar; //build whole text by concatenation
    		i++;
    	}
    	return rotatedPhrase;
    }

	/**
	 * From Part 1 - rotates Single Character;
	 * @param amount
	 * @param singleChar
	 * @return shiftedChar
	 * 
	 */
    public static char rotate(int amount, char singleChar) {
    
    	char shiftedChar = singleChar; 
    	String charToShift = Character.toString(singleChar); 
    	int index = 0;
    	int newIndex = 0;
    	
    	if(alphabetLower.contains(charToShift)) //check for lower case
    	{
    		index = alphabetLower.indexOf(charToShift);
    		newIndex = index + amount;
    		if (newIndex > 25) 
    		{
    			newIndex = newIndex - 26;
    		}
    		else if(newIndex < 0) 
    		{
    			newIndex = newIndex + 26;
    		}
    		shiftedChar = alphabetLower.charAt(newIndex);
    	}
    	else if(alphabetUpper.contains(charToShift)) //check for upper case
    	{
    		index = alphabetUpper.indexOf(charToShift);
    		newIndex = index + amount;
    		if (newIndex > 25) 
    		{
    			newIndex = newIndex - 26;
    		}
    		else if(newIndex < 0) 
    		{
    			newIndex = newIndex + 26;
    		}
    		shiftedChar = alphabetUpper.charAt(newIndex);
    	}
    	
    	return shiftedChar;							
    }
}
