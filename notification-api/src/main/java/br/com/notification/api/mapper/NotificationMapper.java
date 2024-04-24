package br.com.notification.api.mapper;

import br.com.notification.api.model.Notification;
import br.com.notification.api.model.NotificationStatus;
import br.com.notification.api.model.User;
import br.com.notification.api.model.dto.CreateNotificationPayloadDTO;
import br.com.notification.api.model.dto.CreateAndUpdateNotificationResponseDTO;
import br.com.notification.api.model.dto.UpdateNotificationPayloadDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface NotificationMapper {


    @Mappings({
            @Mapping(target = "user.id", ignore = true),
            @Mapping(target = "notificationStatus.id", ignore = true)
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void dtoToEntityIgnoreNullValues(@MappingTarget Notification notification, UpdateNotificationPayloadDTO updateNotificationPayloadDTO);

    @Mappings({
            @Mapping(target = "user.id", source = "userId"),
            @Mapping(target = "notificationStatus.id", source = "notificationStatusId")
    })
    Notification dtoToEntity(CreateNotificationPayloadDTO createNotificationPayloadDTO);

    @Mappings({
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "notificationStatusId", source = "notificationStatus.id")
    })
    CreateAndUpdateNotificationResponseDTO entityToDto(Notification notification);

    @AfterMapping
    default Notification afterMapping(@MappingTarget Notification notification, UpdateNotificationPayloadDTO updateNotificationPayloadDTO) {
        if (notification.getNotificationStatus() != null && notification.getNotificationStatus().getId() == null) {
            notification.setNotificationStatus(null);
        } else if (updateNotificationPayloadDTO.getNotificationStatusId() != null) {
            notification.setNotificationStatus(new NotificationStatus(updateNotificationPayloadDTO.getNotificationStatusId()));
        }

        if (notification.getUser() != null && notification.getUser().getId() == null) {
            notification.setUser(null);
        } else if (updateNotificationPayloadDTO.getUserId() != null) {
            notification.setUser(new User(updateNotificationPayloadDTO.getUserId()));
        }

        return notification;
    }
}
