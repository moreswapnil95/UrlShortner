package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entity.User;
import request.UserRequest;
import service.UserService;

@RestController
@RequestMapping(path = "/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping(path = "/signup")
	public ResponseEntity<String> signUp(@RequestBody UserRequest userRequest){
		
		if(userService.isUsernameAlreadyExist(userRequest.getUsername())) {
			return ResponseEntity.badRequest().body("Username already Exists");
		}
		
		userService.signUp(userRequest);
		
		return ResponseEntity.ok("You sign successfully");
	}
	
	@PostMapping(path = "/login")
	public ResponseEntity<String> login(@RequestBody UserRequest userRequest){
		
		return userService.login(userRequest);
	}
	
	
	@PostMapping(path = "/logout")
	public ResponseEntity<String> logout(@RequestHeader(name = "sessionToken") String sessionToken){
		
		return userService.logout(sessionToken);
	}
	
	//display user detail by id
	@GetMapping(path = "/profile/{id}")
	public User getById(@PathVariable Long id) {
		
		return userService.getUserById(id);
	}
	
	//display user detail by token if user is loggin
	@GetMapping(path = "/profile/bytoken")
	public User getByToken(@RequestHeader(name = "sessionToken") String sessionToken) {
		return userService.getByToken(sessionToken);
	}
	
	
	//display all users
	@GetMapping(path = "/all")
	public List<User> getAllUsers(){
		
		return userService.getAll();
	}
	
	//Delete Operation
	@DeleteMapping(path = "/delete/{id}")
	public String deleteById(@PathVariable Long id) {
		
		return userService.deleteById(id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
