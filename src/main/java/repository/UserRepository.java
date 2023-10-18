package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.User;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);

	User findById(int userid);
	
	boolean existsByUsername(String username);

	User findBySessionToken(String sessionToken);

}


