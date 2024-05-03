package sbmdr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sbmdr.model.User;

@Transactional(readOnly = true)
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    

    @Modifying
    @Transactional
    @Query(value = "update users set password=:newPassword where username=:username ",nativeQuery = true)
    void resetPassword(@Param("newPassword") String newPassword, @Param("username") String username);
}
