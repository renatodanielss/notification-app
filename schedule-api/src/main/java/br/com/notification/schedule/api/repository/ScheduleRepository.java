package br.com.notification.schedule.api.repository;

import br.com.notification.schedule.api.model.Schedule;
import br.com.notification.schedule.api.model.dto.FindScheduleResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query("SELECT NEW br.com.notification.schedule.api.model.dto.FindScheduleResponseDTO( " +
            "   s.id, " +
            "   s.content, " +
            "   s.notificationStatus.name, " +
            "   s.scheduledTime, " +
            "   s.scheduleStatus.name) FROM Schedule s " +
            "INNER JOIN UserSchedule us ON s.id = us.pk.scheduleId " +
            "INNER JOIN User u ON us.pk.userId = u.id " +
            "WHERE (:search IS NULL OR LOWER(s.content) LIKE LOWER(CONCAT('%', CAST(:search AS STRING), '%'))) AND " +
            "   (:userId IS NULL OR u.id = :userId)")
    Page<FindScheduleResponseDTO> findAllByUserIdAndSearch(@Param("userId") Integer userId, @Param("search") String search, Pageable pageable);

    @Query("SELECT s FROM Schedule s WHERE " +
            "   s.scheduledTime BETWEEN :startDate AND :endDate AND " +
            "   (:scheduleStatusIds IS NULL OR s.scheduleStatus.id NOT IN :scheduleStatusIds)")
    List<Schedule> findAllByStatusIdAndScheduledTime(@Param("scheduleStatusIds") List<Integer> scheduleStatusIds,
                                                                    @Param("startDate")Date startDate,
                                                                    @Param("endDate")Date endDate);
}
