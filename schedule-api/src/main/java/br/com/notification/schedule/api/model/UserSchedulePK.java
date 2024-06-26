package br.com.notification.schedule.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSchedulePK implements Serializable {
    @Column(name="user_id")
    private Integer userId;

    @Column(name="schedule_id")
    private Integer scheduleId;
}