package chatserver.webservice;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

import chatserver.Activity;
import chatserver.ChatMessage;
import chatserver.DatabaseConnection;

@Path("/logs")
public class WebServiceLogsResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "Logs web service is running.";
    }    
    
    @Path("/activity")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Activity> activityLogs() {

    	List<Activity> list = new ArrayList<>();
    	
    	Activity[] arr = DatabaseConnection.getChatActivities();
    		
    	for (Activity item : arr)
			list.add(item);
	
        return list;
    }

    @Path("/chat")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ChatMessage> chatLogs() {

    	List<ChatMessage> list = new ArrayList<>();
    	
    	ChatMessage[] arr = DatabaseConnection.getChatLogs();
    		
    	for (ChatMessage item : arr)
			list.add(item);
	
        return list;
    }

}