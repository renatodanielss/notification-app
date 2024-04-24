package br.com.notification.user.api.repository;

import br.com.notification.user.api.model.User;
import br.com.notification.user.api.model.dto.FindUserResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT NEW br.com.notification.user.api.model.dto.FindUserResponseDTO( " +
            "   user.id, " +
            "   user.name, " +
            "   user.email, " +
            "   user.optOut " +
            ") FROM User user " +
            "WHERE (:name IS NULL OR LOWER(user.name) LIKE LOWER(CONCAT('%', CAST(:name AS STRING), '%'))) AND " +
            "(:email IS NULL OR user.email = :email)")
    List<FindUserResponseDTO> findAllByNameAndEmail(@Param("name") String name, @Param("email") String email);
}