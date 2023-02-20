package es.eukariotas.apiservice.persistence.repository;

import es.eukariotas.apiservice.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByUserName(String userName);

    User findByUserNameAndPassword(String userName, String password);

    Boolean existsByUserNameAndToken(String userName, String token);

    Boolean existsByUserNameAndPassword(String userName, String password);
    Boolean existsByUserEmail(String userEmail);
    void deleteTokenByUserName(String userName);

}