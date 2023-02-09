package es.eukariotas.apiservice.persistence.repository;

import es.eukariotas.apiservice.persistence.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TokenRepository extends JpaRepository<Token, Long>{
    Token deleteTokenByToken(String token);


}
