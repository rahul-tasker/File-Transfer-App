
/**
* 	This is  a helping class that uses the string given by the server program
*	as the file name. It then reads the contents of file as a string and passes 
*	the string to the server.   
*/

import java.io.*;
import java.util.Scanner;

public class ServerProcessor {
	StringBuffer buff;
	BufferedReader br;
	String line;

	public String getContents(String fileName) {
		buff = new StringBuffer();
		try {

			File openFile = new File(fileName);
			File f = new File(fileName);

			if (f.isFile()) {
				System.out.println("filename ... " + fileName + " exits");
				Scanner inFile = new Scanner(openFile);
				while (inFile.hasNext()) {
					line = inFile.nextLine();
					System.out.println(line);
					buff.append(line);
					buff.append("\n");
				}
				inFile.close();
				return buff.toString();
			}
			else {
				System.out.println("filename ... " + fileName + " does NOT exit");
			}
		} catch (Exception e) {
		}
		return null;
	} 
} // End class