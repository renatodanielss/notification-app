package br.com.notification.schedule.api;


import br.com.notification.schedule.api.model.Schedule;
import br.com.notification.schedule.api.repository.ScheduleRepository;
import br.com.notification.schedule.api.service.ScheduleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Date;

@SpringBootTest
public class ScheduleApiApplicationTests {

    private ScheduleService scheduleService;

    @Autowired
    public ScheduleApiApplicationTests(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Test
    public void test() {
        Schedule schedule = new Schedule();
        schedule.setContent("Teste Agendamento");
        schedule.setNotificationStatusId(1);
        schedule.setScheduledTime(new Date());
        schedule.setScheduleStatusId(1);
        schedule.setUserIds(Arrays.asList(1, 2));

        Schedule savedSchedule = this.scheduleService.save(schedule);
        Assert.notNull(savedSchedule, "Schedule not null");
    }
}
