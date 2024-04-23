package br.com.notification.schedule.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "schedule")
@Data
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "content")
    @Size(max = 255)
    @NotNull
    private String content;

    @ManyToOne
    @JoinColumn(name = "notification_status_id")
    private NotificationStatus notificationStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "scheduled_time", columnDefinition = "DATETIME")
    private Date scheduledTime;

    @ManyToOne
    @JoinColumn(name = "schedule_status_id")
    private ScheduleStatus scheduleStatus;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_id", insertable = false, updatable = false)
    private List<UserSchedule> userSchedules = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition = "DATETIME", updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", columnDefinition = "DATETIME")
    private Date updatedAt;

    public Schedule(Integer id) {
        this.id = id;
    }

    @PrePersist
    void onPersist() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
        if (this.updatedAt == null) {
            this.updatedAt = new Date();
        }
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = new Date();
    }
}