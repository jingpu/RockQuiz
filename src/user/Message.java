package user;

/**
 * @author youyuan
 * **/

public class Message {

	public final String from;
	public final String to;
	public final String type;
	public final String title;
	public final String content;
	private String time;
	private boolean ifRead;
	private static final String challengeTitle = " wants to challenge you";
	private static final String friendRequestTitle = " wants to add you";
	private static final String friendConfirmTitle = " are friends now";
	private static final String challengeContent = "";
	private static final String friendRequestContent = "";
	private static final String friendConfirmContent = "";
	
	public Message(String from, String to, String type, String title, String content){
		this.from = from;
		this.to = to;
		this.type = type;
		this.ifRead = false;
		if(type.equals("c")){
			this.title = from + challengeTitle;
			this.content = challengeContent + content;
		} else if(type.equals("r")){
			this.title = from + friendRequestTitle;
			this.content = friendRequestContent;
		} else if(type.equals("f")){
			this.title = "You and " + from + friendConfirmTitle;
			this.content = friendConfirmContent;
		} else if(type.equals("n")){
			this.title = title;
			this.content = content;
		} else {
			this.title = "";
			this.content = "";
			System.out.println("Invalid Type");
		}
	}
	
	public Message(String from, String to, String type){
		this(from, to, type, "", "");
	}
	
	public Message(String from, String to, String type, String content){
		this(from, to, type, "", content);
	}
	
	public void setTime(String time){
		this.time = time;
	}
	
	public String getTime(){
		return this.time;
	}
	
	public void setRead(boolean ifRead){
		this.ifRead = ifRead;
	}
	
	public boolean getRead(){
		return this.ifRead;
	}
}
