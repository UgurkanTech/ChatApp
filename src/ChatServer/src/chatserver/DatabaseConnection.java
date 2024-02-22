package chatserver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class DatabaseConnection {
	
	
	public static void saveUserPassword(int user, String pass) {
		try {
			
			String hash = ChatUtils.Password.toHash(pass);
			
			String sql = String.format("insert into passwords values(%s, '%s')", user, hash);
			
			connection.createStatement().executeUpdate(sql);
		
		} catch (SQLException e) {e.printStackTrace();}
		 
	}
	
	
	public static boolean checkUserPassword(int user, String pass) {
		try {
			
			String hash = ChatUtils.Password.toHash(pass);
			
			String sql = String.format("select * from passwords where id=%d", user);

			ResultSet rs = connection.createStatement().executeQuery(sql);
			
			String dbHash = rs.getString("pass");
			
			return dbHash.equals(hash);
		
		} catch (SQLException e) {e.printStackTrace();}
		 
		return false;
	}
	
	public static void logChatMessage(ChatMessage msg) {
		try {
			String sql = String.format("insert into chatlogs values('%s', '%s', '%s', '%s')", msg.date, msg.owner, msg.receiver, msg.content);
			connection.createStatement().executeUpdate(sql);
			
		} catch (SQLException e) {e.printStackTrace();}
		
		//System.out.println(Arrays.toString(getChatLogs()));
		
	}
	
	public static void logChatActivities(Activity activity) {
		try {
			String sql = String.format("insert into activities values('%s', '%s', '%s')", activity.date, activity.user, activity.content);
			connection.createStatement().executeUpdate(sql);
			
		} catch (SQLException e) {e.printStackTrace();}
		
		//System.out.println(Arrays.toString(getChatActivities()));
	}
	
	public static Activity[] getChatActivities() {
		try {
			String sql = String.format("select * from activities");

			ResultSet rs = connection.createStatement().executeQuery(sql);
			
			List<Activity> activities = new ArrayList<Activity>();

	        while(rs.next())
	        {
	        	activities.add(new Activity(rs.getString("date"), rs.getString("user"), rs.getString("content")));
	        }
	        Activity[] arr = new Activity[activities.size()];
	        arr = activities.toArray(arr);
	        return arr;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	public static ChatMessage[] getChatLogs() {
		try {
			String sql = String.format("select * from chatlogs");

			ResultSet rs = connection.createStatement().executeQuery(sql);
			
			List<ChatMessage> messages = new ArrayList<ChatMessage>();

	        while(rs.next())
	        {
	        	messages.add(new ChatMessage(rs.getString("date"), rs.getString("owner"), rs.getString("receiver"), rs.getString("content")));
	        }
	        ChatMessage[] arr = new ChatMessage[messages.size()];
	        arr = messages.toArray(arr);
	        return arr;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static DBPassword[] getDBPasswords() {
		try {
			String sql = String.format("select * from passwords");
	
			ResultSet rs = connection.createStatement().executeQuery(sql);

			List<DBPassword> registers = new ArrayList<DBPassword>();


	        while(rs.next())
	        {
	        	registers.add(new DBPassword(rs.getInt("id"), rs.getString("pass")));
	        }
	        DBPassword[] arr = new DBPassword[registers.size()];
	        arr = registers.toArray(arr);
	        return arr;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static String registerUser(String email, String pass) {
		Employee[] employees = getEmployees();
		
		for (int i = 0; i < employees.length; i++) {
			if (employees[i].email.equals(email)) {
				DBPassword[] registers = getDBPasswords();
				
				for (int j = 0; j < registers.length; j++) {
					if (registers[j].id == employees[i].id) {
						return "0;The user is already registered!";
					}
				}
				
				saveUserPassword(employees[i].id, pass);
				return "1;The user successfully registered!";
			}
		}
		
		return "0;This email doesn't exist for any employee!";
	}
	
	public static String loginUser(String email, String pass) {
		
		Employee[] employees = getEmployees();
		for (int i = 0; i < employees.length; i++) {
			if (employees[i].email.equals(email)) {
				
				DBPassword[] dbPass = getDBPasswords();
				
				boolean exist = false;
				for (int j = 0; j < dbPass.length; j++) {
					if (dbPass[j].id == employees[i].id) {
						exist = true;
					}
				}
				if (!exist) 
					return "0;The employee is not registered!";
				if (checkUserPassword(employees[i].id, pass))
					return "1;Logged in!";
				else
					return "0;Wrong password!";
			}
		}
		return "0;The user is not registered!";
		
	}
	
	
	public static Employee[] getEmployees() {
		try {
			String sql = String.format("select * from employees");
		
			ResultSet rs = connection.createStatement().executeQuery(sql);
			

			List<Employee> employees = new ArrayList<Employee>();


	        while(rs.next())
	        {
	        	employees.add(new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getString("email"), rs.getString("citizenship")));
	
	        }
	        Employee[] arr = new Employee[employees.size()];
	        arr = employees.toArray(arr);
	        return arr;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static void addEmployee(Employee employee) {
		Employee[] employees = getEmployees();
		
		for (int i = 0; i < employees.length; i++) {
			if (employees[i].email.equals(employee.email)) {
				System.out.println("Email already exist for another user!");
				return;
			}
			if (employees[i].id == employee.id) {
				System.out.println("ID already exist for another user!");
				return;
			}
		}
		
		String sql = String.format("insert into employees values(%s, '%s', '%s', '%s', '%s')", employee.id, employee.name, employee.surname, employee.email, employee.citizenship);
		try {
			connection.createStatement().executeUpdate(sql);
			System.out.println("A new employee is added! --- " + employee.email);
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	public static boolean isAdmin(String email) {
		
		try {
			
			int id = -1;
			Employee[] employees = getEmployees();
			for (int i = 0; i < employees.length; i++) {
				if (employees[i].email.equals(email)) {
					id = employees[i].id;
					break;
				}
			}
			if (id == -1) {
				System.out.println("Employee not found!");
				return false;
			}
			
			
			String sql = String.format("select * from admins");
			
			ResultSet rs = connection.createStatement().executeQuery(sql);
			

	        while(rs.next())
	        {
	        	if (rs.getInt("id") == id) {
					return true;
				}
	        }
			
			
		} catch (Exception e) {e.printStackTrace();}
		
		return false;
	}
	
	public static void addAdmin(String email) {
		try {
			int id = -1;
			Employee[] employees = getEmployees();
			for (int i = 0; i < employees.length; i++) {
				if (employees[i].email.equals(email)) {
					id = employees[i].id;
					break;
				}
			}
			if (id == -1) {
				System.out.println("Employee not found!");
				return;
			}
			
			if (isAdmin(email)) {
				System.out.println("The user is already an admin!");
				return;
			}
			
			String sql = String.format("insert into admins values('%s')", id);
			connection.createStatement().executeUpdate(sql);
			
		} catch (SQLException e) {e.printStackTrace();}

	}
	
	
	static Connection connection = null;
	static Statement statement;
	
	
	public static void ConnectDatabase() {
	      try
	      {
	        connection = DriverManager.getConnection("jdbc:sqlite:chatserver.db");
	        statement = connection.createStatement();
	        statement.setQueryTimeout(30); 

	        //statement.executeUpdate("drop table if exists employees");
	        statement.executeUpdate("create table IF NOT EXISTS  employees (id integer PRIMARY KEY, name TEXT, surname TEXT, email TEXT NOT NULL UNIQUE, citizenship TEXT)");
	        //statement.executeUpdate("insert into employees values(1, 'leo', 'tei', 'test@test.com', '13123213221')");
	        //statement.executeUpdate("insert into employees values(2, 'yui', 'koa', 'test2@test.com', '23123131232')");
	        //statement.executeUpdate("insert into employees values(4, 'superuser', 'superuser', 'superuser', '23123145232')");
	        
	        
	        //statement.executeUpdate("drop table if exists chatlogs");
	        statement.executeUpdate("create table IF NOT EXISTS chatlogs (date TEXT, owner TEXT, receiver TEXT, content TEXT)");
	        //statement.executeUpdate("insert into chatlogs values('12:32', 'test@test.com', 'test2@test.com', 'ping')");
	        //statement.executeUpdate("insert into chatlogs values('12:52', 'test2@test.com', 'test@test.com', 'pong')");
	        
	        //statement.executeUpdate("drop table if exists passwords");
	        statement.executeUpdate("create table IF NOT EXISTS passwords (id integer PRIMARY KEY, pass TEXT)");
	        
	        
	        //statement.executeUpdate("drop table if exists activities");
	        statement.executeUpdate("create table IF NOT EXISTS activities (date TEXT, user TEXT, content TEXT)");
	        
	        
	        statement.executeUpdate("create table IF NOT EXISTS admins (id integer PRIMARY KEY)");
	        
	        addEmployee(new Employee(0, "Admin", "Superuser", "superuser@chat.com", "007"));
	        registerUser("superuser@chat.com", "root");
	        addAdmin("superuser@chat.com");
	        
	        
	      }
	      catch(SQLException e){ System.err.println(e.getMessage()); }
	    
	}
	
	public static void main(String[] args) {
		new DatabaseConnection();
		DatabaseConnection.ConnectDatabase();
		

	}
	
  }
