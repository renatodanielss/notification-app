package br.com.notification.api.repository;

import br.com.notification.api.model.User;
import br.com.notification.api.model.dto.FindUserResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT NEW br.com.notification.api.model.dto.FindUserResponseDTO(" +
            "u.id, " +
            "u.optOut" +
            ") FROM User u WHERE u.id = :userId")
    FindUserResponseDTO findUserById(@Param("userId") Integer userid);
}
