package chatserver.webservice;

import java.util.ArrayList;
import java.util.List;

import chatserver.DBPassword;
import chatserver.DatabaseConnection;
import chatserver.Employee;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/data")
public class WebServiceDataResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "Data web service is running.";
    }    
    
    @Path("/employees")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> employees() {

    	List<Employee> list = new ArrayList<>();
    	
    	Employee[] arr = DatabaseConnection.getEmployees();
    		
    	for (Employee item : arr)
			list.add(item);
	
        return list;
    }
    
    @Path("/addemployee={data}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Employee addEmployee(@PathParam("data") String data) {
    	Employee e = null;
    	try {
        	String[] args = data.split(",");
            e = new Employee(Integer.parseInt(args[0]), args[1], args[2], args[3], args[4]);
            DatabaseConnection.addEmployee(e);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
        return e;

    }

    @Path("/registers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DBPassword> registers() {

    	List<DBPassword> list = new ArrayList<>();
    	
    	DBPassword[] arr = DatabaseConnection.getDBPasswords();
    		
    	for (DBPassword item : arr)
			list.add(item);
	
        return list;
    }

}