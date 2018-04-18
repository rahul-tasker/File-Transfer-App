import java.io.IOException;

public class RecieveFileThread implements Runnable {
	String ip;
	String port;
	String fileName;

	public RecieveFileThread(String ip, String port, String fileName) {
		this.ip=ip;
		this.port=port;
		this.fileName=fileName;
	}

	@SuppressWarnings("static-access")
	public void run() {
		String[] arr = new String[]{ip, port, fileName};
		try {
			TCPClient.main(arr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}