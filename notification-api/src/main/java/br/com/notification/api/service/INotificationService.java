package br.com.notification.api.service;

import br.com.notification.api.model.dto.CreateAndUpdateNotificationResponseDTO;
import br.com.notification.api.model.dto.CreateNotificationPayloadDTO;
import br.com.notification.api.model.dto.FindNotificationResponseDTO;
import br.com.notification.api.model.dto.UpdateNotificationPayloadDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface INotificationService {

    CreateAndUpdateNotificationResponseDTO save(CreateNotificationPayloadDTO createNotificationPayloadDTO);

    CreateAndUpdateNotificationResponseDTO partialUpdate(Integer notificationId, UpdateNotificationPayloadDTO updateNotificationPayloadDTO);

    List<FindNotificationResponseDTO> findAllByUserId(Integer userId, Integer notificationStatusId);
}
