package br.com.notification.api.service.notification;

import lombok.Data;

import java.util.Date;

@Data
public class CreateAndUpdateNotificationResponseDTO {
    private Integer id;
    private String content;
    private Integer userId;
    private Integer notificationStatusId;
}
