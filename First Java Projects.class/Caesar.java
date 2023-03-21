/*
 * Assignment 1 - Part 1 - Caesar Cipher
 * Student - Ana Chachua
 */

import java.util.Scanner;

public class Caesar 
{
	/**
	 * this main method allows to encode/decode text
	 * @param args - command line arguments
	 */
	public static void main(String args[]) {
		int len = args.length;
		if(len < 2)
		{
			System.out.println("Too few parameters!");
			System.out.println("Usage: java Caesar n \"cipher text\"");
			
			return;
		}
		if(len > 2)
		{
			System.out.println("Too many parameters!");
			System.out.println("Usage: java Caesar n \"cipher text\"");

			return;
		}
		int num = Integer.parseInt(args[0]);
		String text = args[1];
		Scanner scan = new Scanner(System.in);
		

		if(num > 25 || num < -25)
		{
			num = num % 25;
		}
		
		
		scan.close(); //save resources
		
		text = rotate(num, text);
		System.out.println(text); 
    }
	
    /**
	 * this method rotates a whole string;
	 * @param number
	 * @param phrase
	 * @return rotatedPhrase
	 */
	public static String rotate(int number, String phrase) {

    	int i = 0;
    	char individual; 
    	char rotatedChar; //rotated character
    	
    	String word = phrase; 
    	String rotatedPhrase = ""; 
    	
    	int lenght = word.length();
    	
    	while(i < lenght) 
    	{
    		individual = word.charAt(i);
    		rotatedChar = rotate(number, individual); 
    		rotatedPhrase = rotatedPhrase + rotatedChar; 
    		i++;
    	}
    	return rotatedPhrase;
    }

    /**
	 * this method rotates a single character;
	 * @param amount
	 * @param singleChar
	 * @return shiftedChar
	 */
	 public static char rotate(int amount, char singleChar) {	
    	char shiftedChar = singleChar; //copy the original argument to shifted - if it's not a letter, it remains untouched
    	String charToShift = Character.toString(singleChar); //convert single char to string to find out if the char is contained in either set of alphabet
    	int index = 0;
    	int newIndex = 0;
    	String alphabetLower= "abcdefghijklmnopqrstuvwxyz"; 
    	String alphabetUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
    	
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
