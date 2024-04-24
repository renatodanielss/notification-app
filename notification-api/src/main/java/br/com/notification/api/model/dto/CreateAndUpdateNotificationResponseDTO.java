package br.com.notification.api.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CreateAndUpdateNotificationResponseDTO {
    private Integer id;
    private String content;
    private Integer userId;
    private Integer notificationStatusId;
}
