package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import entity.UrlMapping;
import entity.User;
import repository.UserRepository;
import service.UrlShorteningService;

@RestController
@RequestMapping("/api")
public class UrlShorteningController {
	
	@Autowired
	private UrlShorteningService urlShorteningService;
	
	@Autowired
	private UserRepository userRepository;
	
	
//	@PostMapping("/register")
//	public ResponseEntity<String> registerUser(@RequestBody String username){
//		
//		User existUser = userRepository.findByUsername(username);
//		
//		if(existUser != null) {
//			return ResponseEntity.badRequest().body("Username already exists");
//			
//		}
//		
//		User newUser = new User();
//		newUser.setUsername(username);
//		userRepository.save(newUser);
//		
//		return ResponseEntity.ok("User Registered :" + username);
//	}
	
	
	@PostMapping("/shorten/{userid}")
	public String shortenUrl(@RequestBody String originalUrl,@PathVariable int userid) {
		
		return urlShorteningService.shortenUrl(originalUrl,userid);
	}
	
	
	@GetMapping("/{shortenUrl}")
	public ResponseEntity<Void> getOriginalUrl(@PathVariable String shortenUrl) {
		
		String originalUrl = urlShorteningService.getOriginalUrl(shortenUrl);
		if(originalUrl != null) {
			
//			RedirectView redirectView = new RedirectView(originalUrl);
			urlShorteningService.incrementClickcount(shortenUrl);
			return ResponseEntity.status(HttpStatus.FOUND).header("Location", originalUrl).build();
		}
		return ResponseEntity.notFound().build();
		
	}
	
	@GetMapping("/user/{userid}/urls")
	public List<UrlMapping> getUsersUrls(@PathVariable int userid){
		
		return urlShorteningService.getUsersUrls(userid);
	}
	
	
	@GetMapping("/shorturl/{id}")
	public UrlMapping getUrls(@PathVariable long id) {
		return urlShorteningService.getUrlById(id);
	}
	
	@PutMapping("/updateurl/{id}")
	public ResponseEntity<String> updateUrl(@PathVariable long id,@RequestBody String originalUrl){
		
		return urlShorteningService.updateUrl(id,originalUrl);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteByUrl(@PathVariable long id) {
		
		return urlShorteningService.deleteByUrl(id);
	}

}
