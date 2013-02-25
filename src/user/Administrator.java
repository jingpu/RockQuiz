/**
 * 
 */
package user;

/**
 * @author yang
 *
 */
public class Administrator extends Account{
	public Administrator(String userId) {
		super(userId);
		// TODO Auto-generated constructor stub
	}

	public void deleteAccount(String userId) {
		if(UserManager.deleteAccount(userId)) return;
		System.out.println("Deleting Account Failed");
	}
	
	public void deleteQuiz(String quizname) {
		
	}
	
	
}
