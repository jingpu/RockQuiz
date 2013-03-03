/**
 * 
 */
package user;

/**
 * @author youyuan
 *
 */
public class Administrator extends Account implements User{
	public Administrator(String userId){
		super(userId);
		// TODO Auto-generated constructor stub
	}

	public void deleteAccount(String userId) {
		if(UserManager.deleteAccount(userId)) return;
		System.out.println("Deleting Account Failed");
	}
	
	public void deleteQuiz(String quizname) {
		
	}
	
	public String announce(String accouncement){
		return accouncement;
	}
}
