package ie.gmit.sw;

// import java.util.*;
import java.io.*;
import java.net.*;

public class Parser {
	// private List<String> plain_text = new ArrayList<String>(); //Instance variable of type List (an interface)
	private String plain_text;
	private long start_time;
	private long end_time;
	private long duration;
	
	
	public void load(String source) throws Exception{ //If anything goes wrong, throw the exception to the calling method. Very lazy indeed!
		try {			
			DataInputStream in = null;			
			BufferedReader br = null;			
			String mode = null;
						
			if(source.contains(".txt")) { // read text file	
				
				FileInputStream fstream = new FileInputStream(source); //Wrap the file name in an input stream
				in = new DataInputStream(fstream); //Allows us to read primitive data types (ints, chars, floats) from a stream
				br = new BufferedReader(new InputStreamReader(in)); //Buffers the data input stream
				mode = "text";
			}
			else if(source.contains("www.")) { // read url address
				if(!source.contains("http://")) { // concatenate http:// to the front of the address
					String s = "Http://";
					source = s.concat(source);
				}
				URL url = new URL(source);
				URLConnection urlConnection = url.openConnection();
				HttpURLConnection connection = null;
				if(urlConnection instanceof HttpURLConnection) {
				   connection = (HttpURLConnection) urlConnection;
				   br = new BufferedReader(new InputStreamReader(connection.getInputStream())); //Buffers the url input stream
				   mode = "url";
				}
				else {
				   System.out.println("Please enter an HTTP URL.");
				   return;
				}
			}
	
			StringBuilder sb = new StringBuilder(); // testing
			
			String sourceLine;
			
			start_time = System.currentTimeMillis();
			while ((sourceLine = br.readLine()) != null) { //Loop through each line in the plain text file
				 // plain_text.add(sourceLine); //Add the each string into the array list = fast
				 sb.append(sourceLine);
				 
			}
			plain_text = sb.toString();
			end_time = System.currentTimeMillis();
			duration = (end_time - start_time); // catch the duration of reading process

			if(mode.equals("text")) {
				in.close(); //Good manners to close any in/out streams.
			}
			br.close();

		} catch (Exception e) {
			throw new Exception("[ERROR] Encountered a problem reading the input stream. " + e.getMessage());		
		}
	}
	
	public void showList() { // display text file list in the default directory
		FilenameFilter filter = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        if (name.endsWith(".txt")) {		           
		            return true;
		        } else {
		            return false;
		        }
		    }
		};		
		File folder = new File(System.getProperty("user.dir"));
		String[] file_list = folder.list(filter);		
		for (String file : file_list) {
			System.out.println(file);
		}
	}
	
	/*public int size() {
		return plain_text.size();
	}*/
	
	/*public List<String> getFileContents() {
		return plain_text;
	}*/
	
	public String getStringContents() {
		return plain_text;
	}
	
	public long getDuration() {
		return duration;
	}
}
