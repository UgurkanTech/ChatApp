package chatserver.webservice;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import chatserver.ServerWindow;

import java.net.URI;

public class WebService {

    public static final String BASE_URI = "http://localhost:";
    
    public static String port = "8080";

    public static Server webserver;
    
    public static void startWebService() {
    	
    	try {
			int p = Integer.parseInt(ServerWindow.textField_1.getText());
			port = p + "";
		} catch (Exception e) {
			System.out.println("Error: Invalid webservice port. Using default port:8080");
		}
    	
    	
    	try {
    		final ResourceConfig config = new ResourceConfig().packages("chatserver.webservice");

    		webserver = JettyHttpContainerFactory.createServer(URI.create(BASE_URI + port + "/"), config);

    		Runtime.getRuntime().addShutdownHook(new Thread(() -> {stopWebService();}));

    		
    		System.out.println(String.format("WebService is started at: " + BASE_URI + port + "/"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		
    	
    }
    
    public static void stopWebService() {
		try {
			System.out.println("Shutting down the WebService..");
	    	webserver.stop();
	    	System.out.println("WebService is stopped.");
		} catch (Exception e) { e.printStackTrace();}
    }
    

}
