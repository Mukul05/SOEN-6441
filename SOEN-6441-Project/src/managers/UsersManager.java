package managers;

import entities.UserDetails;

public class UsersManager {

	private static UsersManager instance = new UsersManager();
	
	private UsersManager() {
		
	}
	
	public static UsersManager getInstance() {
		return instance;
	}

	public UserDetails createUser(long id, String name, String designation) {
		// TODO Auto-generated method stub
		UserDetails userDetail = new UserDetails();
		userDetail.setId(id);
		userDetail.setName(name);
		userDetail.setDesignation(designation);
		
		return userDetail;
	}
	
}
