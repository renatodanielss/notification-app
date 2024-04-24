package br.com.notification.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindNotificationResponseDTO {
    private Integer id;
    private String content;
    private String userName;
    private String notificationStatusName;
}
