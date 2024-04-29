package br.com.notification.schedule.api.repository;

import br.com.notification.schedule.api.model.UserSchedule;
import br.com.notification.schedule.api.model.UserSchedulePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserScheduleRepository extends JpaRepository<UserSchedule, UserSchedulePK> {

    @Query("SELECT us FROM UserSchedule us " +
            "WHERE us.pk.scheduleId IN :scheduleIds")
    List<UserSchedule> findAllByScheduleIds(@Param("scheduleIds") List<Integer> scheduleIds);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserSchedule us " +
            "WHERE us.pk.scheduleId = :scheduleId and us.pk.userId not in :userIds")
    void deleteAllByScheduleIdAndUserIds(@Param("scheduleId") Integer scheduleId, @Param("userIds") List<Integer> userIds);
}
