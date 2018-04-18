
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;


public class Client  {

	public static void main(String[] args) throws Exception {
		if ((args.length != 2)) { // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Server> <Port>");
		}

		//INSTRUCTIONS FOR USER
		System.out.println("Please enter 'register [your file]' or 'search [some file]' to begin");
		System.out.println("Additional commands: ");
		System.out.println("rate [ip_address] [file name] [rating between 0 and 5]");
		System.out.println("unregister [file]");
		System.lineSeparator();
		
		while(true){ 
			DatagramSocket clientSocket = new DatagramSocket(); // for network
			BufferedReader fromKeyboard = new BufferedReader(new InputStreamReader(System.in)); // from keyboard
			InetAddress serverAddress = InetAddress.getByName(args[0]); // Server address
			int servPort = Integer.parseInt(args[1]); // get port number
			String clientInput; // from user

			//SEND COMMAND TO SERVER
			clientInput = fromKeyboard.readLine();
			byte[] buf = clientInput.getBytes(); // byte array for data to SEND
			// create a UDP (datagram) packet TO send to server
			DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddress, servPort);
			clientSocket.send(packet); // now send it

			//RECIEVE PACKET FROM SERVER
			byte[] rbuf = new byte[256]; // new byte array to RECEIVE from server
			packet = new DatagramPacket(rbuf, rbuf.length);
			clientSocket.receive(packet); // receive it
			String dataString = new String(packet.getData(),0,packet.getLength());

			//ACTIONS
			//SEARCH FOR FILE
			if(clientInput.contains("search")) {
				if(!dataString.equals("File could not be found")){
					System.out.println("please select which client you want to request the "
							+ "file from by typing the ip and port separated by a space: ");			
					System.out.println(dataString);
					if(!dataString.equals("File could not be found")){
						BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
						String cin = keyboard.readLine(); //IP address

						//CONNECT TO PEER AND RECIEVE
						String[] arr = cin.split(" ");
						Thread request = new Thread(new RecieveFileThread(arr[0], arr[1], clientInput.substring(7, clientInput.length())));
						request.start();
					}
				}
			}

			//RATE THE FILE FOR A GIVEN IP
			else if(clientInput.contains("rate")) {
				System.out.println(dataString);
			}

			//REGISTER A NEW FILE
			else if(clientInput.contains("register") && !clientInput.contains("unregister")) {
				String[] arr = dataString.split(" "); //[registered!, port, file]
				Thread sendThread = new Thread(new SendFileThread(arr[1], arr[2]));
				sendThread.start();
				System.out.println(arr[0]);
			}

			//UNREGISTER FILE
			else if(clientInput.contains("unregister")) {
				System.out.println(dataString);
			}

			//HANDLE INVALID COMMAND
			else {
				System.out.println("Please enter a valid command");
			}
		}
	}
}

