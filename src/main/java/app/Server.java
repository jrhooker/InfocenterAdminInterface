package app;

public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
	private String name;
	private String port;
	private String location;
	private String url;
	private String server;

	public Server(String name, String location, String port, String url){
		this.setLocation(location);
		this.setName(name);
		this.setPort(port);
		this.setUrl(url);
	}
	
	public Server(String name, String location, String port, String url, String server){
		this.setLocation(location);
		this.setName(name);
		this.setPort(port);
		this.setUrl(url);
		this.setServer(server);
	}	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}	
	
}
