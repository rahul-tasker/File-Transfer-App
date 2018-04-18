
/**
* 	This is a TCP server program that receives the name of a file from the client. 
*   It then reads the contents of
*	the file as a string and sends the string back to the file. 
*/

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Server_library {
	Socket sock;
	InputStream recvStream;
	OutputStream sendStream;
	String request;
	String response;

	public Server_library(Socket s) throws IOException, UnknownHostException {
		sock = s;
		recvStream = sock.getInputStream();
		sendStream = sock.getOutputStream();
	} 

	void getRequest() {
		try {
			int dataSize;
			while ((dataSize = recvStream.available()) == 0);
			byte[] recvBuff = new byte[dataSize];
			recvStream.read(recvBuff, 0, dataSize);
			request = new String(recvBuff, 0, dataSize);
		} catch (IOException ex) {
			System.err.println("IOException in getRequest");
		}
	}

	void process() {
		String line;
		try {

			File openFile = new File(request);
			File f = new File(request);

			if (f.isFile()) {
				System.out.println("filename ... " + request + " exits");
				Scanner inFile = new Scanner(openFile);
				while (inFile.hasNext()) {
					line = inFile.nextLine();
					line = line + "\n";
					byte[] sendBuff = line.getBytes();
					sendStream.write(sendBuff, 0, sendBuff.length);
					sendStream.flush();
				}
				inFile.close();
			} else {
				System.out.println("filename ... " + request + " does NOT exit");
				byte[] sendBuff = new byte[100];
				sendBuff = "File Does Not Exist\n".getBytes();
				sendStream.write(sendBuff, 0, sendBuff.length); // send no file
																// line
			}
		} catch (Exception e) {
		}

	}


	void close() {
		try {
			recvStream.close();
			sendStream.close();
			sock.close();
		} catch (IOException ex) {
			System.err.println("IOException in close");
		}
	}
} 