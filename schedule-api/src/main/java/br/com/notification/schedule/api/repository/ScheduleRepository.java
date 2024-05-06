package br.com.notification.schedule.api.repository;

import br.com.notification.schedule.api.model.Schedule;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends ReactiveMongoRepository<Schedule, String> {

}
