package br.com.notification.api.service;

import br.com.notification.api.mapper.NotificationMapper;
import br.com.notification.api.model.Notification;
import br.com.notification.api.model.dto.*;
import br.com.notification.api.repository.NotificationRepository;
import br.com.notification.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
@SuppressWarnings("unused")
public class NotificationService implements INotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository,
                               NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.notificationMapper = notificationMapper;
    }

    @Override
    @Transactional
    public CreateAndUpdateNotificationResponseDTO save(CreateNotificationPayloadDTO createNotificationPayloadDTO) {
        FindUserResponseDTO findUserResponseDTO = userRepository.findUserById(createNotificationPayloadDTO.getUserId());
        if (findUserResponseDTO == null) {
            throw new EntityNotFoundException("User not found!");
        }

        if (findUserResponseDTO.getOptOut()) {
            throw new IllegalArgumentException("It's not possible to create notification to a user marked as opt-out.");
        }

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

        FindUserResponseDTO findUserResponseDTO = userRepository.findUserById(notification.getUser().getId());
        if (findUserResponseDTO == null) {
            throw new EntityNotFoundException("User not found for this notification!");
        }

        if (findUserResponseDTO.getOptOut()) {
            throw new IllegalArgumentException("It's not possible to update a notification to a user marked as opt-out.");
        }

        this.notificationMapper.dtoToEntityIgnoreNullValues(notification, updateNotificationPayloadDTO);
        this.notificationRepository.save(notification);

        return this.notificationMapper.entityToDto(notification);
    }

    @Override
    @Transactional
    public Page<FindNotificationResponseDTO> findAllByUserId(Integer userId, Integer notificationStatusId, Pageable pageable) {
        return this.notificationRepository.findAllByUserId(userId, notificationStatusId, pageable);
    }
}
