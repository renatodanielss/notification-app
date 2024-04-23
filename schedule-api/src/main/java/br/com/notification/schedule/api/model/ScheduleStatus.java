package br.com.notification.schedule.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "schedule_status")
@Data
@NoArgsConstructor
public class ScheduleStatus {

    public static final int PENDING = 1;
    public static final int IN_PROGRESS = 2;
    public static final int FINISHED = 3;
    public static final int CANCELED = 4;
    public static final int ERROR = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @Size(max = 20)
    @NotNull
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition = "DATETIME", updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", columnDefinition = "DATETIME")
    private Date updatedAt;

    public ScheduleStatus(Integer id) {
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