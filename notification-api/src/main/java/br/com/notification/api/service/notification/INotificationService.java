package br.com.notification.api.service.notification;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface INotificationService {

    CreateAndUpdateNotificationResponseDTO save(CreateNotificationPayloadDTO createNotificationPayloadDTO);

    CreateAndUpdateNotificationResponseDTO partialUpdate(Integer notificationId, UpdateNotificationPayloadDTO updateNotificationPayloadDTO);

    List<FindNotificationResponseDTO> findAllByUserId(Integer userId, Integer notificationStatusId);
}
