package br.com.notification.schedule.api.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateAndUpdateScheduleResponseDTO {
    private Integer id;
    private String content;
    private Integer notificationStatusId;
    private Date scheduledTime;
    private Integer scheduleStatusId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Integer> userIds;
}
