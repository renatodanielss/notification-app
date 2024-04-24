package br.com.notification.api.model.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateNotificationPayloadDTO {
    @Size(max = 255)
    private String content;

    private Integer userId;

    private Date scheduledTime;

    private Integer notificationStatusId;
}