package br.com.notification.schedule.api.repository;

import br.com.notification.schedule.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u.id FROM User u " +
            "WHERE u.id IN :userIds AND " +
            "      u.optOut IS TRUE")
    List<Integer> findOptOutByUserIds(@Param("userIds") List<Integer> userIds);
}
