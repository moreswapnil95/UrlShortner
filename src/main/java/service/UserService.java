package service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import entity.User;
import repository.UserRepository;
import request.UserRequest;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;

	public boolean isUsernameAlreadyExist(String username) {
		// TODO Auto-generated method stub
		return userRepository.existsByUsername(username);
	}

	public void signUp(UserRequest userRequest) {
		// TODO Auto-generated method stub
		String mdString = DigestUtils.md5Hex(userRequest.getPassword());
		userRequest.setPassword(mdString);
		User user = new User(userRequest);
		userRepository.save(user);
	}

	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).orElseThrow();
	}

	public ResponseEntity<String> login(UserRequest userRequest) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsername(userRequest.getUsername());
		
		if(user != null) {
			String encoded = DigestUtils.md5Hex(userRequest.getPassword());
			if(!encoded.equals(user.getPassword())) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password is wrong Try Again!");
			}		
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email not exist Try again!");
		}
		
		String sessionTokenString = UUID.randomUUID().toString();
		user.setSessionToken(sessionTokenString);
		userRepository.save(user);
		
		return ResponseEntity.ok(sessionTokenString);
	}

	public List<User> getAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	public String deleteById(Long id) {
		// TODO Auto-generated method stub
		 userRepository.deleteById(id);
		 return "one user delted";
	}

	public ResponseEntity<String> logout(String sessionToken) {
		// TODO Auto-generated method stub
		User user = userRepository.findBySessionToken(sessionToken);
		
		if(user != null) {
			user.setSessionToken(null);
			userRepository.save(user);
			return ResponseEntity.ok("Logged Out Successsfully");
		}
		return ResponseEntity.badRequest().body("not Register or Logged In!");
		
	}

	public User getByToken(String sessionToken) {
		// TODO Auto-generated method stub
		User user = userRepository.findBySessionToken(sessionToken);
		if(user != null) {
			return user;
		}
		return null;
	}




	
	

	

}










