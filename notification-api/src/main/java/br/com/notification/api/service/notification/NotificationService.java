package br.com.notification.api.service.notification;

import br.com.notification.api.mapper.NotificationMapper;
import br.com.notification.api.model.Notification;
import br.com.notification.api.repository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
@SuppressWarnings("unused")
public class NotificationService implements INotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }

    @Override
    @Transactional
    public CreateAndUpdateNotificationResponseDTO save(CreateNotificationPayloadDTO createNotificationPayloadDTO) {
        Notification notification = this.notificationMapper.dtoToEntity(createNotificationPayloadDTO);
        notification = this.notificationRepository.save(notification);

        return this.notificationMapper.entityToDto(notification);
    }

    @Override
    @Transactional
    public CreateAndUpdateNotificationResponseDTO partialUpdate(Integer notificationId, UpdateNotificationPayloadDTO updateNotificationPayloadDTO) {
        Optional<Notification> notificationOptional = this.notificationRepository.findById(notificationId);
        if (notificationOptional.isEmpty()) {
            throw new EntityNotFoundException("Entity Notification not found!");
        }
        Notification notification = notificationOptional.get();

        this.notificationMapper.dtoToEntityIgnoreNullValues(notification, updateNotificationPayloadDTO);
        this.notificationRepository.save(notification);

        return this.notificationMapper.entityToDto(notification);
    }

    @Override
    @Transactional
    public List<FindNotificationResponseDTO> findAllByUserId(Integer userId, Integer notificationStatusId) {
        return this.notificationRepository.findAllByUserId(userId, notificationStatusId);
    }
}
