/**
 * 
 */
package user;

/**
 * @author youyuan
 *
 */
public class Announce {
	private final String time;
	private final String content;
	private final String admin;
	public Announce(String time, String content, String admin){
		this.time = time;
		this.content = content;
		this.admin = admin;
	}
	public String getTime(){
		return time;
	}
	public String getContent(){
		return content;
	}
	public String getAdmin(){
		return admin;
	}
}
