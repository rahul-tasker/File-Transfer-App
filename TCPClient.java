import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {

	Socket sock;
	OutputStream sendStream;
	InputStream recvStream;
	String request;
	String response;

	public static void main(String[] args) throws IOException {
		final String servName = args[0];
		final int servPort = Integer.parseInt(args[1]);
		String fileName = args[2];
		try {
			if (fileName.equals("exit")){
				//Do nothing
			} else {
				Client_library client = new Client_library(servName, servPort, fileName);
				client.makeRequest();
				System.out.println("make request done...");
				client.sendRequest();
				System.out.println("send request done...");
				client.getResponse();
				System.out.println("got response done...");
				client.useResponse();
				System.out.println("if it exists, save the file locally done...");
				client.close();
				System.out.println("close stuff done..."); 
				// means only one file per tcp session
			} 
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			System.out.println("Connection refused, probably no server running");
		}
	}
}
