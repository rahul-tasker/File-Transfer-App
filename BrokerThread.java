import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BrokerThread implements Runnable {
	String ip;
	String port;

	public BrokerThread(String ip, String port) {
		this.ip=ip;
		this.port=port;
	}

	public void run() {
		//SET UP
		try {
			while(true){ 
				DatagramSocket clientSocket = new DatagramSocket(); // for network
				BufferedReader fromKeyboard = new BufferedReader(new InputStreamReader(System.in)); // from keyboard
				InetAddress serverAddress = InetAddress.getByName(ip); // Server address
				int servPort = Integer.parseInt(port); // get port number
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
				String serverData = new String(packet.getData(),0,packet.getLength());

				//ACTIONS
				//SEARCH FOR FILE
				if(clientInput.contains("search")) {
					if(!serverData.equals("File could not be found")){
						System.out.println("please select which client you want to request the "
								+ "file from by typing the ip and port separated by a space: ");			
						System.out.println(serverData);
						BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
						String cin = keyboard.readLine(); //IP address

						// CONNECT TO PEER AND RECIEVE
						String[] arr = cin.split(" ");
						Thread request = new Thread(new RecieveFileThread(arr[0], arr[1], clientInput.substring(7, clientInput.length())));
						request.start();
					}
				}

				//RATE THE FILE FOR A GIVEN IP
				else if(clientInput.contains("rate")) {
					System.out.println(serverData);
				}

				//REGISTER A NEW FILE
				else if(clientInput.contains("register")) {
					System.out.println(serverData);
				}

				//UNREGISTER FILE
				else if(clientInput.contains("unregister")) {
					System.out.println(serverData);
				}

				//HANDLE INVALID COMMAND
				else {
					System.out.println("Please enter a valid command");
				}
			}
		}
		catch(IOException e){
			System.err.println("IOException");
		}
	}
}