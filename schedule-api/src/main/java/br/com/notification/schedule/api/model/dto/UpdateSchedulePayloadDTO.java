package br.com.notification.schedule.api.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UpdateSchedulePayloadDTO {
    @Size(max = 255)
    private String content;

    private Integer notificationStatusId;

    private List<Integer> userIds;

    private Date scheduledTime;
    private Integer scheduleStatusId;
}