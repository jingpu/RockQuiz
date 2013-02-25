package login;

import java.util.*;

public class AccountManager {
	
	private HashMap<String, String> accntManager;
    
    public AccountManager() {
        accntManager = new HashMap<String, String>();
        accntManager.put("Patrick", "1234");
        accntManager.put("Molly", "FloPup");
    }
	
    public boolean alreadyExist(String usrname) {
    	if (accntManager.containsKey(usrname)) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean infoMatch(String usrname, String pwd) {
    	if (accntManager.containsKey(usrname)) {
    		String truePwd = accntManager.get(usrname);
    		if (pwd.equals(truePwd)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public void createNewAccount(String usrname, String pwd) {
    	accntManager.put(usrname, pwd);
    }
    
}

