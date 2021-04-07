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

public class Bigram {

	public static void main(String[] args) {	
		/*
		 * This treemap maps words to words that can come after
		 */
		TreeMap<String,List<String>> bigramMap = new TreeMap<String,List<String>>();
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
		//The current first word
		String first = null;
		int wordCount = 0;
		
		while(tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken().toLowerCase();
			if(first == null)first = token;
			else{
				
				String second = token;
				
				if(bigramMap.get(first) != null) {
					List<String> mappedList = bigramMap.get(first);
					mappedList.add(second);
				}
				else {
					List<String> mappedList = new LinkedList<String>();
					mappedList.add(second);
					bigramMap.put(first, mappedList);
				}
				
				//Update the first and second string
				first = second;
			}
			++wordCount;
		}
		
		int count = 100;
		Set<String> setArray = bigramMap.keySet();
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

		newSentence = words;
		for(int i = 0;i < count;++i) {
			
			List<String> nextList = bigramMap.get(words);
			
			//If we find a word that has nothing after, add a period and move on...
			if(nextList == null) {
				randIndex = (int)(Math.random() * keyList.size());
				//This is the current pair of two words.
				words = keyList.get(randIndex);
				
				newSentence = newSentence + ". " + words;
				continue;
			}
			randIndex = (int)(Math.random() * nextList.size());
			String secondWord = nextList.get(randIndex);

			
			
			
			
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
				
				newSentence = newSentence + " " + words;
				continue;
			}
			
			words = secondWord;
		}
		newSentence = newSentence + ".";

		System.out.println(newSentence);
	}

}