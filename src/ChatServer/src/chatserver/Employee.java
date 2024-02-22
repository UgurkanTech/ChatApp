package chatserver;

public class Employee {

	int id;
	String name;
	String surname;
	String email;
	String citizenship;
	
	public Employee(int id, String name, String surname, String email, String citizenship) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.citizenship = citizenship;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCitizenship() {
		return citizenship;
	}
	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", citizenship="
				+ citizenship + "]";
	}
	
	
	

}
