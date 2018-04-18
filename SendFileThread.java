import java.io.IOException;
import java.net.UnknownHostException;

public class SendFileThread implements Runnable {
	String ip;
	String port;
	String fileName;

	public SendFileThread(String port, String fileName) {
		this.ip = ip;
		this.port=port;
		this.fileName=fileName;
	}

	public void run() {
		String[] arr = new String[]{port};
		try {
			TCPServer.main(arr);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}