package chatserver;

public class DBPassword {

	int id;
	String pass;
	public DBPassword(int id, String pass) {
		super();
		this.id = id;
		this.pass = pass;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	@Override
	public String toString() {
		return "DBPassword [id=" + id + ", pass=" + pass + "]";
	}
	
	
}
