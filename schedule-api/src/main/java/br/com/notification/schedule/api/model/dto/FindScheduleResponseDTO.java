package br.com.notification.schedule.api.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindScheduleResponseDTO {
    private Integer id;
    private String content;
    private String notificationStatusName;
    private Date scheduledTime;
    private String scheduleStatusName;
    private List<Integer> userIds;

    public FindScheduleResponseDTO(Integer id, String content, String notificationStatusName, Date scheduledTime, String scheduleStatusName) {
        this.id = id;
        this.content = content;
        this.notificationStatusName = notificationStatusName;
        this.scheduledTime = scheduledTime;
        this.scheduleStatusName = scheduleStatusName;
    }
}
