package entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ChangeEmailRequest {
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
	  
	    private String token;

	    @OneToOne(targetEntity = AppUser.class, fetch = FetchType.EAGER)
	    @JoinColumn(nullable = false, name = "user_id")
	    private AppUser user;
	  
	    private String newEmail;
	    
	    private boolean accepted;
	    
	    public ChangeEmailRequest() {};

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public AppUser getUser() {
			return user;
		}

		public void setUser(AppUser user) {
			this.user = user;
		}

		public boolean isAccepted() {
			return accepted;
		}

		public void setAccepted(boolean accepted) {
			this.accepted = accepted;
		}

		public String getNewEmail() {
			return newEmail;
		}

		public void setNewEmail(String newEmail) {
			this.newEmail = newEmail;
		}
		
}
