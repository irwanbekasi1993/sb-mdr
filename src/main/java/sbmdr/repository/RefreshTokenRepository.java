package sbmdr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sbmdr.model.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long>{
    Optional<RefreshToken> findById(Long id);

    Optional<RefreshToken> findByToken(String token);
    
}
