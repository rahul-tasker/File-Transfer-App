import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server {
	public static void main(String argv[]) throws IOException {
		ArrayList<Data> addresses_in_use = new ArrayList<Data>();
		ArrayList<ArrayList<String>> requests = new ArrayList<ArrayList<String>>();
		//SET UP
		System.out.println("Server Running...");
		if (argv.length != 1) { // Test for correct argument list
			throw new IllegalArgumentException("Parameter(s): <Port>");
		}
		int servPort = Integer.parseInt(argv[0]);
		DatagramSocket serverSocket = new DatagramSocket(servPort);
		// process a datagram
		byte[] rbuf = new byte[256];
		byte[] sbuf = new byte[256];

		while (true) {
			//RECIEVE PACKET FROM CLIENT
			String output = "";
			DatagramPacket packet = new DatagramPacket(rbuf, rbuf.length);
			System.out.println("RECIEVING...");
			serverSocket.receive(packet);
			// Obtain a byte input stream to really read the UDP packet
			ByteArrayInputStream bin = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
			// Connect a reader for easier access
			BufferedReader reader = new BufferedReader(new InputStreamReader(bin));
			String line = reader.readLine(); 
			System.out.println("RECIEVED...");

			//RESPONSE TO COMMANDS
			if (line.contains("register") && !line.contains("unregister")){
				int port_to_use = servPort+1;
				System.out.println("REGISTERING FILE...");
				//register file
				String file = line.substring(9, line.length());
				Data data = new Data(packet.getAddress().toString(), Integer.toString(port_to_use), file, "5");
				addresses_in_use.add(data);
				System.out.println(addresses_in_use);
				output += "Registered! " + port_to_use + " " + file;
			}
			else if (line.contains("unregister")) {
				//unregister file
				System.out.println("UNREGISTERING FILE...");
				String file = line.substring(11, line.length());
				for(int i=0; i<addresses_in_use.size(); i++) {
					Data data = addresses_in_use.get(i);
					if(data.getIp().equals(packet.getAddress().toString()) && data.getFile().equals(file)) {
						addresses_in_use.remove(i);
					}
				}
				System.out.println(addresses_in_use);
				output+= "You have unregistered file: " + file;
			}
			else if (line.contains("search")) {
				System.out.println("SEARCHING FOR FILE...");
				//search for file
				System.out.println(addresses_in_use);
				boolean isFile = false;
				String file = line.substring(7, line.length());
				output+= "Your search results are: " + System.lineSeparator();
				for(int i=0; i<addresses_in_use.size();i++){
					Data data = addresses_in_use.get(i);
					if(data.getFile().equals(file)) {
						isFile = true;
						output += data.getIp() + "  ||  " + data.getPort() + " || "+ data.getRating() + System.lineSeparator();
					}
				}
				if(!isFile) {
					output+="File could not be found";
				}
			}
			else if(line.contains("request")){
				ArrayList<String> sublist = new ArrayList<String>();
				String[] arr = line.split(" "); //[request, file, ip, port]
				sublist.add(packet.getAddress().toString());
				sublist.add(arr[2]);
				sublist.add(arr[3]);
				sublist.add(arr[1]);
				requests.add(sublist);
				output+= "file requested. please wait for user to send it.";
			}
			else if (line.contains("rate")) {
				System.out.println("RATING YOUR FILE...");
				//rate the host
				String[] arr = line.split(" ");  
				String ip = "/" + arr[1];
				String fileName = arr[2];
				System.out.println(ip);
				System.out.println(fileName);
				int rating = Integer.parseInt(arr[3]);
				for(int i=0; i<addresses_in_use.size(); i++) {
					Data temp = addresses_in_use.get(i);
					System.out.println(temp.getIp());
					System.out.println(temp.getFile());
					if(temp.getIp().equals(ip) && temp.getFile().equals(fileName)) {
						System.out.println("MATCH FOUND!");
						int currentRating = Integer.parseInt(temp.getRating());
						int new_rating = (currentRating+rating)/2;
						temp.setRating(Integer.toString(new_rating));
						addresses_in_use.set(i, temp);
						output += "Thank you for your input";
					}
				}
				if(output == "") {
					output += "No File match";
				}
			}
			
			//SEND PACKET BACK TO CLIENT
			InetAddress address = packet.getAddress();
			int port = packet.getPort();
			System.out.println("SENDING: " + output);
			sbuf = output.getBytes();
			// create a datagram packet
			packet = new DatagramPacket(sbuf, sbuf.length, address, port);
			serverSocket.send(packet);
		}
	}
}
