import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Trigram {

	public static void main(String[] args) {	
		/*
		 * This treemap maps pairs of words to words that can come after
		 */
		TreeMap<String,List<String>> trigramMap = new TreeMap<String,List<String>>();
		FileReader fileReader;
		String str = "";
		try {
			fileReader = new FileReader(new File("inputText.txt"));
			BufferedReader reader = new BufferedReader(fileReader);
			String s = reader.readLine();
			
			while(s != null) {
				str = str + s + " ";
				s = reader.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringTokenizer tokenizer = new StringTokenizer(str);
		//The current first and second word.
		String first = null, second = null;
		int wordCount = 0;
		
		while(tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken().toLowerCase();
			if(first == null)first = token;
			else if(second == null)second = token;
			else{
				String newStr = first + " " + second;
				
				String third = token;
				
				if(trigramMap.get(newStr) != null) {
					List<String> mappedList = trigramMap.get(newStr);
					mappedList.add(third);
				}
				else {
					List<String> mappedList = new LinkedList<String>();
					mappedList.add(third);
					trigramMap.put(newStr, mappedList);
				}
				
				//Update the first and second string
				first = second;
				second = third;
			}
			++wordCount;
		}
		
		int count = 100;
		Set<String> setArray = trigramMap.keySet();
		List<String> keyList = new ArrayList<String>();
		for(String thisString : setArray)keyList.add(thisString);
		String newSentence;
		
		int randIndex = (int)(Math.random() * keyList.size());
		//This is the current pair of two words.
		String words = keyList.get(randIndex);
		
		//it was dark and cold
		
		//it was 
		//firstWord = was
		//secondWord = dark
		//was dark
		
		//This is the second word so that we can generate the next pair
		String firstWord = words.substring(words.indexOf(' ') + 1);
		newSentence = words;
		for(int i = 0;i < count;++i) {
			List<String> nextList = trigramMap.get(words);
			
			//If we find a word that has nothing after, add a period and move on...
			if(nextList == null) {
				randIndex = (int)(Math.random() * keyList.size());
				//This is the current pair of two words.
				words = keyList.get(randIndex);
				
				firstWord = words.substring(words.indexOf(' ') + 1);
				newSentence = newSentence + ". " + words;
				continue;
			}
			randIndex = (int)(Math.random() * nextList.size());
			String secondWord = nextList.get(randIndex);

			
			words = firstWord + " " + secondWord;
			
			
			
			//Add a newline after every 15 words ish..
			if(((i + 1) % 16) == 0) {
				newSentence = newSentence + "\n";
			}
			//Otherwise add a space...
			else {
				newSentence = newSentence + " ";
			}
			newSentence = newSentence + secondWord;
			
			
			if(secondWord.endsWith(".") || secondWord.endsWith("?") || secondWord.endsWith("!")) {
				randIndex = (int)(Math.random() * keyList.size());
				//This is the current pair of two words.
				words = keyList.get(randIndex);
				
				firstWord = words.substring(words.indexOf(' ') + 1);
				newSentence = newSentence + " " + words;
				continue;
			}
			
			
			//Advancing the first word to be the next one
			firstWord = secondWord;
			
			
			
		}
		newSentence = newSentence + ".";

		System.out.println(newSentence);
	}

}
