package br.com.notification.schedule.api.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_schedule")
@Data
@NoArgsConstructor
public class UserSchedule {
    @EmbeddedId
    private UserSchedulePK pk;

    public UserSchedule(UserSchedulePK pk) {
        this.pk = pk;
    }
}