package main;

import java.util.HashMap;

public class SoupManager {
	/**
	   * This method receives two strings and checks that message can be constructed
	   * with letters contained in the soup string
	   * It is optimized for speed and large amounts of data specially in the soup string
	   */
	public boolean CheckMessageInSoupOriginal(String message, String soup) 
	{
		boolean bResult = false;
		HashMap<KeyChar, MutableInteger> messageHash = new HashMap<KeyChar, MutableInteger>();
		
		KeyChar helperKey = new KeyChar();
		
		// Preprocess Message. Store every char with the number of times it appears in message in a HashMap
		long messageLength = message.length();
		for (int i = 0; i < messageLength; i++)
		{
			char c = message.charAt(i);
			helperKey.SetValue(c);
			
			// Try get letter from HashMap
			MutableInteger numTimesAtMessage = messageHash.get(helperKey);
			
			// Letter not added to HashMap before
			if (numTimesAtMessage == null)
			{
				numTimesAtMessage = new MutableInteger(1);
				KeyChar newKey = new KeyChar(c);
				messageHash.put(newKey, numTimesAtMessage);
			}
			// Letter already in HashMap
			else
			{
				numTimesAtMessage.Increment();
			}
		}
		
		// Search in Soup
		int individualLettersMessage = messageHash.size();
		long i = 0;
		long soupLength = soup.length();
		while (!bResult && i < soupLength)
		{
			char c = soup.charAt((int) i);
			helperKey.SetValue(c);
			
			// Try to get Letter Entry from MessageHash
			MutableInteger numTimesAtMessage = messageHash.get(helperKey);
			
			// This soup letter is needed for message
			if (numTimesAtMessage != null)
			{
				// Decrement the number of letters of this kind needed
				numTimesAtMessage.Decrement();
				// If we are finish with this individual letter, decrement counter
				if (numTimesAtMessage.GetValue() == 0)
				{
					// This letter is done
					individualLettersMessage--;
					
					// Check if this was the last letter and we have finish
					if (individualLettersMessage == 0)
						bResult = true;
					
				}
			}
			
			i++;
		}
		
		return bResult;
	}
	
	public boolean CheckMessageInSoup_NOKEYCHAR(String message, String soup) 
	{
		boolean bResult = false;
		HashMap<Character, MutableInteger> messageHash = new HashMap<Character, MutableInteger>();
		
		// Preprocess Message. Store every char with the number of times it appears in message in a HashMap
		long messageLength = message.length();
		for (int i = 0; i < messageLength; i++)
		{
			char c = message.charAt(i);
			Character C = c;
			
			// Try get letter from HashMap
			MutableInteger numTimesAtMessage = messageHash.get(C);
			
			// Letter not added to HashMap before
			if (numTimesAtMessage == null)
			{
				numTimesAtMessage = new MutableInteger(1);
				Character newKey = new Character(c);
				messageHash.put(newKey, numTimesAtMessage);
			}
			// Letter already in HashMap
			else
			{
				numTimesAtMessage.Increment();
			}
		}
		
		// Search in Soup
		int individualLettersMessage = messageHash.size();
		long i = 0;
		long soupLength = soup.length();
		while (!bResult && i < soupLength)
		{
			char c = soup.charAt((int) i);
			Character C = c;
			
			// Try to get Letter Entry from MessageHash
			MutableInteger numTimesAtMessage = messageHash.get(C);
			
			// This soup letter is needed for message
			if (numTimesAtMessage != null)
			{
				// Decrement the number of letters of this kind needed
				numTimesAtMessage.Decrement();
				// If we are finish with this individual letter, decrement counter
				if (numTimesAtMessage.GetValue() == 0)
				{
					// This letter is done
					individualLettersMessage--;
					
					// Check if this was the last letter and we have finish
					if (individualLettersMessage == 0)
						bResult = true;
					
				}
			}
			
			i++;
		}
		
		return bResult;
	}
	
	public boolean CheckMessageInSoup_NOMUTABLE(String message, String soup) 
	{
		boolean bResult = false;
		HashMap<KeyChar, Integer> messageHash = new HashMap<KeyChar, Integer>();
		
		KeyChar helperKey = new KeyChar();
		
		// Preprocess Message. Store every char with the number of times it appears in message in a HashMap
		long messageLength = message.length();
		for (int i = 0; i < messageLength; i++)
		{
			char c = message.charAt(i);
			helperKey.SetValue(c);
			
			// Try get letter from HashMap
			Integer numTimesAtMessage = messageHash.get(helperKey);
			
			// Letter not added to HashMap before
			if (numTimesAtMessage == null)
			{
				numTimesAtMessage = new Integer(1);
				KeyChar newKey = new KeyChar(c);
				messageHash.put(newKey, numTimesAtMessage);
			}
			// Letter already in HashMap
			else
			{
				// Increment number of occurrences
				Integer numTimesAtMessage_PP = numTimesAtMessage+1;
				// Replace in Hash
				messageHash.replace(helperKey, numTimesAtMessage, numTimesAtMessage_PP);
			}
		}
		
		// Search in Soup
		int individualLettersMessage = messageHash.size();
		long i = 0;
		long soupLength = soup.length();
		while (!bResult && i < soupLength)
		{
			char c = soup.charAt((int) i);
			helperKey.SetValue(c);
			
			// Try to get Letter Entry from MessageHash
			Integer numTimesAtMessage = messageHash.get(helperKey);
			
			// This soup letter is needed for message
			if (numTimesAtMessage != null)
			{
				// Decrement the number of letters of this kind needed
				Integer numTimesAtMessage_MM = numTimesAtMessage-1;
				// Replace in Hash
				messageHash.replace(helperKey, numTimesAtMessage, numTimesAtMessage_MM);
				// If we are finish with this individual letter, decrement counter
				if (numTimesAtMessage_MM == 0)
				{
					// This letter is done
					individualLettersMessage--;
					
					// Check if this was the last letter and we have finish
					if (individualLettersMessage == 0)
						bResult = true;
					
				}
			}
			
			i++;
		}
		
		return bResult;
	}
	
	
	public boolean CheckMessageInSoup_NOKEYCHAR_NOMUTABLE(String message, String soup) 
	{
		boolean bResult = false;
		HashMap<Character, Integer> messageHash = new HashMap<Character, Integer>();
		
		// Preprocess Message. Store every char with the number of times it appears in message in a HashMap
		long messageLength = message.length();
		for (int i = 0; i < messageLength; i++)
		{
			char c = message.charAt(i);
			Character C = c;
			
			// Try get letter from HashMap
			Integer numTimesAtMessage = messageHash.get(C);
			
			// Letter not added to HashMap before
			if (numTimesAtMessage == null)
			{
				numTimesAtMessage = new Integer(1);
				messageHash.put(C, numTimesAtMessage);
			}
			// Letter already in HashMap
			else
			{
				// Increment number of occurrences
				Integer numTimesAtMessage_PP = numTimesAtMessage+1;
				// Replace in Hash
				messageHash.replace(C, numTimesAtMessage, numTimesAtMessage_PP);
			}
		}
		
		// Search in Soup
		int individualLettersMessage = messageHash.size();
		long i = 0;
		long soupLength = soup.length();
		while (!bResult && i < soupLength)
		{
			char c = soup.charAt((int) i);
			Character C = c;
			
			// Try to get Letter Entry from MessageHash
			Integer numTimesAtMessage = messageHash.get(C);
			
			// This soup letter is needed for message
			if (numTimesAtMessage != null)
			{
				// Decrement the number of letters of this kind needed
				Integer numTimesAtMessage_MM = numTimesAtMessage-1;
				// Replace in Hash
				messageHash.replace(C, numTimesAtMessage, numTimesAtMessage_MM);
				
				// If we are finish with this individual letter, decrement counter
				if (numTimesAtMessage_MM == 0)
				{
					// This letter is done
					individualLettersMessage--;
					
					// Check if this was the last letter and we have finish
					if (individualLettersMessage == 0)
						bResult = true;
				}
				
			}
			
			i++;
		}
		
		return bResult;
	}


	/**
	   * This class is used as a custom key in HashMap and wraps a single char
	   * It customizes equals and hashCode part for this particular case to gain speed
	   * without sacrificing its functionality
	   */
	class KeyChar implements Comparable<KeyChar> 
	{
		private char value = ' ';

		KeyChar(char value) {
			this.value = value;
		}
		
		KeyChar() {
		}

		public void SetValue(char c)
		{
			value = c;
		}
		
		@Override
		public int compareTo(KeyChar o) {
			return Character.compare(this.value, o.value);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			KeyChar key = (KeyChar) o;
			return value == key.value;
		}

		@Override
		public int hashCode() {
			return value;
		}
	}

	/**
	   * This is a wrapper class of an integer type primitive that allows internal value 
	   * modification unlike Integer class
	   */
	class MutableInteger {
		
		private int value;
		
		public MutableInteger(int i)
		{
			value = i;
		}
		
		public int GetValue()
		{
			return value;
		}
		
		public void SetValue(int i)
		{
			value = i;
		}
		
		public void Increment()
		{
			value++;
		}
		
		public void Decrement()
		{
			value--;
		}
	}

}



