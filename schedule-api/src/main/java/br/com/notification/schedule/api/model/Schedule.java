package br.com.notification.schedule.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
@Data
@NoArgsConstructor
public class Schedule {
    @Id
    private String id;

    @Size(max = 255)
    @NotNull
    private String content;

    @NotNull
    private Integer notificationStatusId;

    @NotNull
    private Date scheduledTime;

    @NotNull
    private Integer scheduleStatusId;

    @NotBlank
    @NotNull
    private List<Integer> userIds;

    private Date createdAt = new Date();

    private Date updatedAt = new Date();

    public Schedule(String id) {
        this.id = id;
    }
}