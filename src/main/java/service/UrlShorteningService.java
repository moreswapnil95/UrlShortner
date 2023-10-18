package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import entity.UrlMapping;
import entity.User;
import repository.UrlMappingRepository;
import repository.UserRepository;

@Service
public class UrlShorteningService {
	
	private static final String BASE62_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 8;
	
	@Autowired
	private UrlMappingRepository urlMappingRepository;
	
	@Autowired
	private UserRepository userRepository;

	public String shortenUrl(String originalUrl,int userid) {
		// TODO Auto-generated method stub
		
		User user = userRepository.findById(userid);
		if(user == null) {
			throw new IllegalArgumentException("uner not found");
		}
		
		UrlMapping urlMapping = urlMappingRepository.findByOriginalUrlAndUser(originalUrl,user);
		
		if(urlMapping != null) {
			return urlMapping.getShortUrl();
		}
		
		String shortUrl = generateShortUrl(); 
		
		urlMapping = new UrlMapping();
		urlMapping.setOriginalUrl(originalUrl);
		urlMapping.setShortUrl(shortUrl);
		urlMapping.setUser(user);
		urlMappingRepository.save(urlMapping);
		
		return shortUrl;
	}

	private String generateShortUrl() {
		// TODO Auto-generated method stub
		StringBuilder shortUrlBuilder = new StringBuilder();
		for(int i=0;i<SHORT_URL_LENGTH;i++) {
			int randomIndex = (int) (Math.random()*BASE62_CHARACTERS.length());
			shortUrlBuilder.append(BASE62_CHARACTERS.charAt(randomIndex));
		}
		
		return shortUrlBuilder.toString();
	}

	public String getOriginalUrl(String shortenUrl) {
		// TODO Auto-generated method stub
		UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortenUrl);
		if(urlMapping != null) {
			return urlMapping.getOriginalUrl();
		}
		
		return null;
	}

	public List<UrlMapping> getUsersUrls(int userid) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userid);
		if(user == null) {
			return null;
		}
		
		return urlMappingRepository.findByUser(user);
	}

	public void incrementClickcount(String shortenUrl) {
		// TODO Auto-generated method stub
		UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortenUrl);
		
		if(urlMapping != null) {
			urlMapping.setClickCount(urlMapping.getClickCount()+1);
			urlMappingRepository.save(urlMapping);
		}
		
	}

	public UrlMapping getUrlById(long id) {
		// TODO Auto-generated method stub
		return urlMappingRepository.findById(id).orElseThrow(null);
	}

	public ResponseEntity<String> updateUrl(long id, String originalUrl) {
		// TODO Auto-generated method stub
		UrlMapping urlMapping = urlMappingRepository.findById(id).orElseThrow(null);
		
		if(urlMapping.getOriginalUrl().equalsIgnoreCase(originalUrl)) {
			return ResponseEntity.ok("Already Exist");
		}
		
		urlMapping.setOriginalUrl(originalUrl);
		urlMappingRepository.save(urlMapping);
		return ResponseEntity.ok("Url updated");
	}

	public String deleteByUrl(long id) {
		// TODO Auto-generated method stub
		urlMappingRepository.deleteById(id);
		return "one url deleted";
	}

}
