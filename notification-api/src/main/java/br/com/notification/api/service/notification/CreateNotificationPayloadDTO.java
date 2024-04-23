package br.com.notification.api.service.notification;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class CreateNotificationPayloadDTO {
    @Size(max = 255)
    @NotNull
    private String content;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer notificationStatusId;
}