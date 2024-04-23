package br.com.notification.schedule.api.mapper;

import br.com.notification.schedule.api.model.Notification;
import br.com.notification.schedule.api.model.Schedule;
import br.com.notification.schedule.api.model.dto.FindScheduleResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface NotificationMapper {


    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    Notification scheduleToNotification(Schedule schedule);
}
