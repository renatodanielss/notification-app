package br.com.notification.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class FindNotificationResponseDTO {
    private Integer id;
    private String content;
    private String userName;
    private String notificationStatusName;
    private Date createdAt;
}
