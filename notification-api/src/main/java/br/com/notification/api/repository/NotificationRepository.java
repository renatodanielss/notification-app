package br.com.notification.api.repository;

import br.com.notification.api.model.Notification;
import br.com.notification.api.model.dto.FindNotificationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query("SELECT NEW br.com.notification.api.model.dto.FindNotificationResponseDTO(" +
            "n.id, " +
            "n.content, " +
            "n.user.name, " +
            "n.notificationStatus.name," +
            "n.createdAt" +
            ") FROM Notification n WHERE n.user.id = :userId AND " +
            " (:notificationStatusId IS NULL OR n.notificationStatus.id = :notificationStatusId)")
    Page<FindNotificationResponseDTO> findAllByUserId(@Param("userId") Integer userid,
                                                      @Param("notificationStatusId") Integer notificationStatusId,
                                                      Pageable pageable);
}