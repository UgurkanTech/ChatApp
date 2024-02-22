import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConfigManager {

	
	public static String getIP() {
		String data = "127.0.0.1:777";
		try {
		      File myObj = new File("serverip.txt");
		      Scanner myReader = new Scanner(myObj);
		      
		      while (myReader.hasNextLine()) {
			        data = myReader.nextLine();        
		      }
		      myReader.close();
		 } catch (FileNotFoundException e) {
		      System.out.println("An error occurred while reading config. Using default ip..");
		      e.printStackTrace();
		 }
		return data;
	}

}
