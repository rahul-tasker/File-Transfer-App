
public class Data {
	public String IP;
	public String FILE;
	public String RATING;
	public String PORT;
	public Data(String ip, String port, String file, String rating){
		this.IP = ip;
		this.FILE = file;
		this.RATING = rating;
		this.PORT = port;
	}
	
	public String getIp() {
		return IP;
	}
	
	public String getPort() {
		return PORT;
	}
	
	public String getFile() {
		return FILE;
	}
	
	public String getRating() {
		return RATING;
	}
	
	public void setIp(String ip) {
		this.IP = ip;
	}

	public void setPort(String port) {
		this.PORT = port;
	}
	
	public void setFile(String file) {
		this.FILE = file;
	}
	
	public void setRating(String rating) {
		this.RATING = rating;
	}


}
