package entity;

import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import request.UserRequest;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "usertable")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false,unique = true)
	private String name;
	
	@Column(nullable = false,unique = true)
	private String username;
	
	@Column(nullable = false,unique = true)
	private String password;
	
	
	private String sessionToken;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	@JsonBackReference
	private List<UrlMapping> urlMappings;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(long id, String name, String username, String password, String sessionToken,
			List<UrlMapping> urlMappings) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.sessionToken = sessionToken;
		this.urlMappings = urlMappings;
	}

	public User(UserRequest userRequest) {
		// TODO Auto-generated constructor stub
		name=userRequest.getName();
		username=userRequest.getUsername();
		password = userRequest.getPassword();
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public List<UrlMapping> getUrlMappings() {
		return urlMappings;
	}

	public void setUrlMappings(List<UrlMapping> urlMappings) {
		this.urlMappings = urlMappings;
	}

	
	

	

	
	
}
