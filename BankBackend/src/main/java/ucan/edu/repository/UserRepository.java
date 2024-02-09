package ucan.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Repository;
import ucan.edu.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  UserDetails findByLogin(String login);
}
