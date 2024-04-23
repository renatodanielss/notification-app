package br.com.notification.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "notification_status")
@Data
@NoArgsConstructor
public class NotificationStatus {

    public static final int UNREAD = 1;
    public static final int READ = 2;

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

    public NotificationStatus(Integer id) {
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