package chatserver;

public class ChatMessage {

	String date;
	String owner;
	String receiver;
	String content;
	
	public ChatMessage(String date, String owner, String receiver, String content) {
		super();
		this.date = date;
		this.owner = owner;
		this.receiver = receiver;
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "ChatMessage [date=" + date + ", owner=" + owner + ", receiver=" + receiver + ", content=" + content
				+ "]";
	}
	
	

}
