package br.com.notification.schedule.api.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {


//    @Mappings({
//            @Mapping(target = "id", ignore = true),
//            @Mapping(target = "createdAt", ignore = true),
//            @Mapping(target = "updatedAt", ignore = true)
//    })
//    Notification scheduleToNotification(Schedule schedule);
}
