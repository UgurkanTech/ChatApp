package chatserver;

public class Activity {

	String date;
	String user;
	String content;
	public Activity(String date, String user, String content) {
		super();
		this.date = date;
		this.user = user;
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Activity [date=" + date + ", user=" + user + ", content=" + content + "]";
	}
	
	
	
}
