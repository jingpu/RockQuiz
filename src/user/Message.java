package user;

public class Message {
	public final String time;
	public final String from;
	public final String to;
	public final String type;
	public final String title;
	public final String content;
	public boolean ifRead;
	private static final String challengeTitle = " Wants To CHALLENGE You";
	private static final String friendRequestTitle = " Wants To Be Your Friend";
	private static final String friendConfirmTitle = " Are FRIENDS NOW";
	private static final String challengeContent = "";
	private static final String friendRequestContent = "";
	private static final String friendConfirmContent = "";
	
	public Message(String time, String from, String to, String type, String title, String content){
		this.time = time;
		this.from = from;
		this.to = to;
		this.type = type;
		if(type.equals("c")){
			this.title = challengeTitle;
			this.content = challengeContent;
		} else if(type.equals("r")){
			this.title = friendRequestTitle;
			this.content = friendRequestContent;
		} else if(type.equals("f")){
			this.title = friendConfirmTitle;
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
	
	public Message(String time, String from, String to, String type){
		this(time, from, to, type, "", "");
	}
}
