package ie.gmit.sw;

import java.io.BufferedWriter;
import java.io.FileWriter;
// import java.util.*;

public class PortaCipher{

	/*
	  The following tableau is used by the Porta Cipher:
		
	  Keys| A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
	  ---------------------------------------------------------
	  A,B | N O P Q R S T U V W X Y Z A B C D E F G H I J K L M
	  C,D | O P Q R S T U V W X Y Z N M A B C D E F G H I J K L
	  E,F | P Q R S T U V W X Y Z N O L M A B C D E F G H I J K 
	  G,H | Q R S T U V W X Y Z N O P K L M A B C D E F G H I J
	  I,J | R S T U V W X Y Z N O P Q J K L M A B C D E F G H I
	  K,L | S T U V W X Y Z N O P Q R I J K L M A B C D E F G H
	  M,N | T U V W X Y Z N O P Q R S H I J K L M A B C D E F G
	  O,P | U V W X Y Z N O P Q R S T G H I J K L M A B C D E F
	  Q,R | V W X Y Z N O P Q R S T U F G H I J K L M A B C D E
	  S,T | W X Y Z N O P Q R S T U V E F G H I J K L M A B C D
	  U,V | X Y Z N O P Q R S T U V W D E F G H I J K L M A B C
	  W,X | Y Z N O P Q R S T U V W X C D E F G H I J K L M A B
	  Y,Z | Z N O P Q R S T U V W X Y B C D E F G H I J K L M A
	
	
	  keyword: DATASTRUCTURESANDALGORITHMS
	  Plain Text: THECURFEWTOLLSTHEKNELLOFPARTINGDAY  
	  
	  (1) Repeat the keyword above the plain text
		DATASTRUCTURESANDALGORITHMSDATASTR
		THECURFEWTOLLSTHEKNELLOFPARTINGDAY
		
	  (2) For each character in the plain text
		
			Find the character at the intersection of the row
			containing the key character and the column containing the 
			plain text.
		    
			K: DATASTRUCTURESANDALGORITHMSDATASTR
			P: THECURFEWTOLLSTHEKNELLOFPARTINGDAY
			   ----------------------------------
			C: FUNPLINOIKETNJGNSXIUSTKOMTIFVETZWD
		
	The encryption and decryption processes are identical. Encrypting 
	a piece of text twice with the same key will return the original text:
		
		K: DATASTRUCTURESANDALGORITHMSDATASTR
		C: FUNPLINOIKETNJGNSXIUSTKOMTIFVETZWD
		   ----------------------------------
		P: THECURFEWTOLLSTHEKNELLOFPARTINGDAY
		
		
	Next year, I'll show you how to break ciphers...!
	*/

	// private List<String> list_cipher_text = new ArrayList<String>(); //Instance variable of type List (an interface)
	// private List<String> list_plain_text = new ArrayList<String>(); //Instance variable of type List (an interface)
	private String cipher_text;
	// private String plain_text;
	private long start_time;
	private long end_time;
	private long encrypt_duration;
	private long decrypt_duration;
	
	public static final String[][] tableau = {
		{"KEYS", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"},
		  {"AB", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"},
		  {"CD", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "N", "M", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"},
		  {"EF", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "N", "O", "L", "M", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"},
		  {"GH", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "N", "O", "P", "K", "L", "M", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"},
		  {"IJ", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "N", "O", "P", "Q", "J", "K", "L", "M", "A", "B", "C", "D", "E", "F", "G", "H", "I"},
		  {"KL", "S", "T", "U", "V", "W", "X", "Y", "Z", "N", "O", "P", "Q", "R", "I", "J", "K", "L", "M", "A", "B", "C", "D", "E", "F", "G", "H"},
		  {"MN", "T", "U", "V", "W", "X", "Y", "Z", "N", "O", "P", "Q", "R", "S", "H", "I", "J", "K", "L", "M", "A", "B", "C", "D", "E", "F", "G"},
		  {"OP", "U", "V", "W", "X", "Y", "Z", "N", "O", "P", "Q", "R", "S", "T", "G", "H", "I", "J", "K", "L", "M", "A", "B", "C", "D", "E", "F"},
		  {"QR", "V", "W", "X", "Y", "Z", "N", "O", "P", "Q", "R", "S", "T", "U", "F", "G", "H", "I", "J", "K", "L", "M", "A", "B", "C", "D", "E"},
		  {"ST", "W", "X", "Y", "Z", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "E", "F", "G", "H", "I", "J", "K", "L", "M", "A", "B", "C", "D"},
		  {"UV", "X", "Y", "Z", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "A", "B", "C"},
		  {"WX", "Y", "Z", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "A", "B"},
		  {"YZ", "Z", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "A"}
	};
	
	/*public void encrypt(String keyword, List<String> plain_text) throws Exception{
		try {
			List<String> temp_cipher_text = new ArrayList<String>();
			String altered_keyword = keyword.toUpperCase();
			char[] altered_plain_text = plain_text.toString().replaceAll("[^a-zA-Z]", "").toUpperCase().toCharArray();
			BufferedWriter bw = new BufferedWriter(new FileWriter("cipher.txt"));

			System.out.println("length: " + altered_plain_text.length);
			
			start_time = System.currentTimeMillis();

			for(int i = 0; i < altered_plain_text.length; i++) { // ##for loop of String
				
	            int mode = i % altered_keyword.length();

	            for(int k = 1; k < 14; k++) { // loop through the keyword set ie. "AB", "CD" ..."YZ"	   	            
	            	if(tableau[k][0].indexOf(altered_keyword.charAt(mode)) != -1) { // find the match each keyword letter from the keyword set
	            			            	
	            		for(int j = 1; j < 27; j++) { // loop through the plain texts set ie. "A", "B" ... "Z"   
	            			if(tableau[0][j].indexOf(altered_plain_text[i]) != -1) { // find the match each plain texts letter from the keyword set
	            				temp_cipher_text.add(tableau[k][j]); // check difference between add and addAll
	            				bw.write(tableau[k][j]);
	            			}
	            		}
	            	}           
	            }
			} // ##for loop
			list_cipher_text = temp_cipher_text;
			// list_cipher_text.addAll(temp_cipher_text);
			end_time = System.currentTimeMillis();
			encrypt_duration = (end_time - start_time);

			// temp_cipher_text.clear(); // clean temp array list
			bw.flush();
			bw.close();
			
		} catch (Exception e) {
			throw new Exception("[ERROR] Encountered a problem writing the file. " + e.getMessage());		
		}
	}
	
	public void decrypt(String keyword, List<String> cipher_text) throws Exception{
		try {
			List<String> temp_plain_text = new ArrayList<String>();
			String altered_keyword = keyword.toUpperCase();
			char[] altered_cipher_text = cipher_text.toString().replaceAll("[^a-zA-Z]", "").toCharArray();
			BufferedWriter bw = new BufferedWriter(new FileWriter("original.txt"));
			
			System.out.println("length: " + altered_cipher_text.length);

			if(altered_cipher_text.length == 0) {
				System.out.println("Your cipher text is empty, please encrypt the plain text first");
			}
			else {
				start_time = System.currentTimeMillis();
				
				for(int i = 0; i < altered_cipher_text.length; i++) { // ##for loop of String
					
		            int mode = i % altered_keyword.length();

		            for(int k = 1; k < 14; k++) { // loop through the keyword set ie. "AB", "CD" ..."YZ"	   	            
		            	if(tableau[k][0].indexOf(altered_keyword.charAt(mode)) != -1) { // find the match each keyword letter from the keyword set
		            			            	
		            		for(int j = 1; j < 27; j++) { // loop through the plain texts set ie. "A", "B" ... "Z"   
		            			if(tableau[0][j].indexOf(altered_cipher_text[i]) != -1) { // find the match each plain texts letter from the keyword set
		            				temp_plain_text.add(tableau[k][j]); // check difference between add and addAll
		            				bw.write(tableau[k][j]);
		            			}
		            		}
		            	}           
		            }
				} // ##for loop
				list_plain_text = temp_plain_text;
				// list_plain_text.addAll(temp_plain_text);
				end_time = System.currentTimeMillis();
				decrypt_duration = (end_time - start_time);

				// temp_plain_text.clear(); // clean temp array list
				bw.flush();
				bw.close();
			}

		} catch (Exception e) {
			throw new Exception("[ERROR] Encountered a problem writing the file. " + e.getMessage());		
		}
	}*/
	
	public void encrypt(String keyword, String plain_text) throws Exception{
		try {
			String altered_keyword = keyword.toUpperCase();
			String altered_plain_text = plain_text.replaceAll("[^a-zA-Z]", "").toUpperCase();
			BufferedWriter bw = new BufferedWriter(new FileWriter("cipher.txt"));
			
			// System.out.println("p length: " + altered_plain_text.length());
			// System.out.println("p contents: " + test_plain_text);

			start_time = System.currentTimeMillis();
			StringBuilder sb = new StringBuilder(); // testing
			for(int i = 0; i < altered_plain_text.length(); i++) { // ##for loop of String
				
	            int mode = i % altered_keyword.length();

	            for(int k = 1; k < 14; k++) { // loop through the keyword set ie. "AB", "CD" ..."YZ"	   	            
	            	if(tableau[k][0].indexOf(altered_keyword.charAt(mode)) != -1) { // find the match each keyword letter from the keyword set
	            			            	
	            		for(int j = 1; j < 27; j++) { // loop through the plain texts set ie. "A", "B" ... "Z"   
	            			if(tableau[0][j].indexOf(altered_plain_text.charAt(i)) != -1) { // find the match each plain texts letter from the keyword set
	            				sb.append(tableau[k][j]); // check difference between add and addAll
	            				bw.write(tableau[k][j]);
	            			}
	            		}
	            	}           
	            }
			} // ##for loop
			cipher_text = sb.toString();
			end_time = System.currentTimeMillis();
			encrypt_duration = (end_time - start_time);

			bw.flush();
			bw.close();
			
		} catch (Exception e) {
			throw new Exception("[ERROR] Encountered a problem writing the file. " + e.getMessage());		
		}
	}
	
	public void decrypt(String keyword, String cipher_text) throws Exception{
		try {
			String altered_keyword = keyword.toUpperCase();
			// String altered_cipher_text = cipher_text.replaceAll("[^a-zA-Z]", "").toUpperCase();
			BufferedWriter bw = new BufferedWriter(new FileWriter("original.txt"));
			
			// System.out.println("c length: " + cipher_text.length());
			// System.out.println("c contents: " + cipher_text);

			if(cipher_text.length() == 0) {
				System.out.println("Your cipher text is empty, please encrypt the plain text first");
			}
			else {
				start_time = System.currentTimeMillis();
				// StringBuilder sb = new StringBuilder(); // testing
				for(int i = 0; i < cipher_text.length(); i++) { // ##for loop of String
					
		            int mode = i % altered_keyword.length();

		            for(int k = 1; k < 14; k++) { // loop through the keyword set ie. "AB", "CD" ..."YZ"	   	            
		            	if(tableau[k][0].indexOf(altered_keyword.charAt(mode)) != -1) { // find the match each keyword letter from the keyword set
		            			            	
		            		for(int j = 1; j < 27; j++) { // loop through the plain texts set ie. "A", "B" ... "Z"   
		            			if(tableau[0][j].indexOf(cipher_text.charAt(i)) != -1) { // find the match each plain texts letter from the keyword set
		            				// sb.append(tableau[k][j]); // check difference between add and addAll
		            				bw.write(tableau[k][j]);
		            			}
		            		}
		            	}           
		            }
				} // ##for loop
				// plain_text = sb.toString();
				end_time = System.currentTimeMillis();
				decrypt_duration = (end_time - start_time);

				bw.flush();
				bw.close();
			}

		} catch (Exception e) {
			throw new Exception("[ERROR] Encountered a problem writing the file. " + e.getMessage());		
		}
	}
	
	/*public List<String> getFileContents() {
		return list_cipher_text;
	}*/
	
	public String getStringContents() {
		return cipher_text;
	}
	
	public long getEncDuration() {
		return encrypt_duration;
	}
	
	public long getDecDuration() {
		return decrypt_duration;
	}
}