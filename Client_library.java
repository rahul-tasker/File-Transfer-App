
/**
This is a simple FTP protocol. 
The client requests the name of the file; 
the server sends the contents of the file. 
The name of the file is passed to the program as the command argument. 
*/

import java.net.*;
import java.io.*;

public class Client_library {
	Socket sock;
	OutputStream sendStream;
	InputStream recvStream;
	String request;
	String response;
	String fileName;
	Boolean noFile = false;

	//CONSTRUCTOR
	Client_library(String server, int port, String fileName) throws IOException, UnknownHostException {
		this.fileName = fileName;
		sock = new Socket(server, port);
		sendStream = sock.getOutputStream();
		recvStream = sock.getInputStream();
	}

	//REQUEST FILE NAME
	void makeRequest() {
		request = fileName;
	}

	//REQUEST FILE FROM SERVER WITH FILE NAME
	void sendRequest() {
		try {
			byte[] sendBuff = new byte[request.length()];
			sendBuff = request.getBytes();
			System.out.println("sending filename to server...");
			sendStream.write(sendBuff, 0, sendBuff.length);
			System.out.println("sent filename...");
		} catch (IOException ex) {
			System.err.println("IOException in sendRequest");
		}
	} 

	// GET RESPONSE FROM SERVER
	void getResponse() throws IOException {
		int dataSize = 9999; // large files
		int size;
		try {
			File file = new File(fileName);
			if(!file.exists())
				file.createNewFile();
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);
			byte[] recvBuff = new byte[dataSize];
			while ((size = recvStream.read(recvBuff, 0, dataSize)) != -1) {

				response = new String(recvBuff, 0, size);
				if (response.contains("File Does Not Exist")) {
					System.out.println("no file");
					noFile = true;
					return;
				}
				System.out.println(response);
				bw.write(response);
			}
			bw.close();
		// catch when client is done
		} catch (EOFException e) { 
			System.out.println("goodbye server ");
			sock.close(); // Close the socket. We are done with this client!
		} catch (SocketException e) {
			System.out.println("Connection closed prematurely");
			sock.close();

		} catch (IOException ex) {
			System.err.println("IOException in getResponse");
		}
	} 
	
	// STORE RESPONSE
	void useResponse() {
		if (!noFile) {
			ClientUser user = new ClientUser();
			user.store(fileName, response);
		} else {
			System.out.println("Remember, File Does Not Exist");
		}
	}

	// CLOSE CONNECTON WITH SERVER
	void close() {
		try {
			sendStream.close();
			recvStream.close();
			sock.close();
		} catch (IOException ex) {
			System.err.println("IOException in close");
		}
	} 

}