package br.com.notification.schedule.api.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateSchedulePayloadDTO {
    @Size(max = 255)
    @NotNull
    private String content;

    @NotNull
    private List<Integer> userIds;

    @NotNull
    private Integer notificationStatusId;

    @NotNull
    private Date scheduledTime;
    @NotNull
    private Integer scheduleStatusId;
}