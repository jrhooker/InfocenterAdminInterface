package app;

import java.io.IOException;
import java.net.Socket;

public class PortOpen {

	
	public static void main(String[] args) {
	
		PortOpen po = new PortOpen();
		
		boolean port = po.available(80, "localhost");
		
		System.out.println("port:" + port);
	}
	

	public boolean available(int port, String host) {
	    System.out.println("--------------Testing port " + port);
	    Socket s = null;
	    try {
	        s = new Socket(host, port);

	        // If the code makes it this far without an exception it means
	        // something is using the port and has responded.
	        System.out.println("--------------Port " + port + " is not available");
	        return false;
	    } catch (IOException e) {
	        System.out.println("--------------Port " + port + " is available");
	        return true;
	    } finally {
	        if( s != null){
	            try {
	                s.close();
	            } catch (IOException e) {
	                throw new RuntimeException("You should handle this error." , e);
	            }
	        }
	    }
	}
	
	public boolean isUp(int port, String host) {
	    System.out.println("--------------Testing port " + port);
	    Socket s = null;
	    try {
	        s = new Socket(host, port);
	        // If the code makes it this far without an exception it means
	        // something is using the port and has responded.
	        System.out.println("--------------Port " + port + " is up");
	        return true;
	    } catch (IOException e) {
	        System.out.println("--------------Port " + port + " is down");
	        return false;
	    } finally {
	        if( s != null){
	            try {
	                s.close();
	            } catch (IOException e) {
	                throw new RuntimeException("You should handle this error." , e);
	            }
	        }
	    }
	}
	
}
