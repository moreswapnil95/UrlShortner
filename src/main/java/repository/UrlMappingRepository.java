package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.UrlMapping;
import entity.User;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {


	UrlMapping findByShortUrl(String shortenUrl);

	UrlMapping findByOriginalUrl(String originalUrl);

	UrlMapping findByOriginalUrlAndUser(String originalUrl, User user);

	List<UrlMapping> findById(int userid);

	List<UrlMapping> findByUser(User user);

}
