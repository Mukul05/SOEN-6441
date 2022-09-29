package bean;

import javax.persistence.*;

@Entity
@Table(name = "userdetail")
public class UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "firstName", nullable = false, length = 150)
	private String firstName;
	
	@Column(name = "lastName", nullable = false, length = 150)
	private String lastName;
	
	@Column(name = "email", nullable = false, length = 150)
	private String email;
	
	public void UserDetails() {}
	public void UserDetails(int id) {
		this.id = id;
	}

	public UserDetails(int id,String firstName, String lastName, String email ) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}
	public void setFirstName(String lastName) {
		this.lastName = lastName;
	}
	public String getLastName() {
		return this.lastName;
	}
	public void setLastName(String firstName) {
		this.firstName = firstName;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

		
}
