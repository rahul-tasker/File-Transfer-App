import java.net.*;
import java.io.*;

public class TCPServer {
	Socket sock;
	InputStream recvStream;
	OutputStream sendStream; 
	String request;
	String response;

	public static void main(String[] args) throws UnknownHostException, IOException {
		final int port = Integer.parseInt (args[0]);
		ServerSocket listenSock = new ServerSocket (port);
		System.out.println("server ready...");
		Server_library server = new Server_library (listenSock.accept ());
		server.getRequest ();    
		System.out.println("got request complete...");
		server.process ();
		System.out.println("process complete...");
		//server.sendResponse ();
		System.out.println("sending to client complete...");
		server.close ();//closes tcp data socket to client => not persistent data
		System.out.println("server done...");
	}
}
