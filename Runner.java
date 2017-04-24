package ie.gmit.sw;

import java.util.*;

public class Runner {

	public static void main(String[] args) throws Exception{
		// declare variables			
		String source, keyword;
		Parser ps = new Parser();
		PortaCipher pc = new PortaCipher();
		int selection;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Please enter the keyword");
		keyword = sc.nextLine();
		// keyword = "DATASTRUCTURESANDALGORITHMS"; // test keyword
		System.out.println("Keyword: [" + keyword + "]");
		
		System.out.println("Please enter a file name or an url address");
		System.out.println("------------- <List of files> -------------");		
		ps.showList();
		System.out.println("-------------------------------------------");
		
		source = sc.nextLine();				

		ps.load(source);

		System.out.println("parse: " + ps.getDuration() + " ms");

		System.out.println("Please choose from the menu");
		System.out.println("1: Encrypt 2: Decrypt 3: Set New Keyword 4: Set New Resource 5: quit");						
		
		selection = sc.nextInt();				
		sc.nextLine(); // clear scanner buffer
		
		while(selection != 5){
			switch (selection) {
			case 1:				
				System.out.println("Encrypt");
				//pc.encrypt(keyword, ps.getFileContents());
				pc.encrypt(keyword, ps.getStringContents());
				System.out.println("Encryption: " + pc.getEncDuration() + " ms");
				break;
			case 2:				
				System.out.println("Decrypt");				
				//pc.decrypt(keyword, pc.getFileContents());
				pc.decrypt(keyword, pc.getStringContents());
				System.out.println("Decryption: " + pc.getDecDuration() + " ms");
				break;
			case 3:
				System.out.println("New Keyword");
				System.out.println("Please enter the keyword");
				keyword = sc.nextLine();
				System.out.println("Keyword: [" + keyword + "]");

				break;
			case 4:
				System.out.println("New Parse");
				System.out.println("Please enter a file name or an url address");
				System.out.println("------------- <List of files> -------------");		
				ps.showList();
				System.out.println("-------------------------------------------");
				source = sc.nextLine();								
				ps.load(source);
				System.out.println("parse: " + ps.getDuration() + " ms");
				break;
			default:				
				break;
			}
			System.out.println("Please choose from the menu");
			System.out.println("1: Encrypt 2: Decrypt 3: Set New Keyword 4: Set New Resource 5: quit");
			selection = sc.nextInt();
			sc.nextLine(); // clear scanner buffer
		}
		System.out.println("bye bye");
		
		// close and clear scanner
		sc.close();

	}
	
}
