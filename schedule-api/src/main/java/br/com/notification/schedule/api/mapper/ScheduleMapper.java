package br.com.notification.schedule.api.mapper;

import br.com.notification.schedule.api.model.NotificationStatus;
import br.com.notification.schedule.api.model.Schedule;
import br.com.notification.schedule.api.model.ScheduleStatus;
import br.com.notification.schedule.api.model.User;
import br.com.notification.schedule.api.model.dto.CreateAndUpdateScheduleResponseDTO;
import br.com.notification.schedule.api.model.dto.CreateSchedulePayloadDTO;
import br.com.notification.schedule.api.model.dto.UpdateSchedulePayloadDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {


    @Mappings({
            @Mapping(target = "notificationStatus.id", ignore = true),
            @Mapping(target = "scheduleStatus.id", ignore = true)
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void dtoToEntityIgnoreNullValues(@MappingTarget Schedule schedule, UpdateSchedulePayloadDTO updateSchedulePayloadDTO);

    @Mappings({
            @Mapping(target = "notificationStatus.id", source = "notificationStatusId"),
            @Mapping(target = "scheduleStatus.id", source = "scheduleStatusId")
    })
    Schedule dtoToEntity(CreateSchedulePayloadDTO createSchedulePayloadDTO);

    @Mappings({
            @Mapping(target = "notificationStatusId", source = "notificationStatus.id"),
            @Mapping(target = "scheduleStatusId", source = "scheduleStatus.id")
    })
    CreateAndUpdateScheduleResponseDTO entityToDto(Schedule schedule);

    @AfterMapping
    default Schedule afterMapping(@MappingTarget Schedule schedule, UpdateSchedulePayloadDTO updateSchedulePayloadDTO) {
        if (schedule.getNotificationStatus() != null && schedule.getNotificationStatus().getId() == null) {
            schedule.setNotificationStatus(null);
        } else if (updateSchedulePayloadDTO.getNotificationStatusId() != null) {
            schedule.setNotificationStatus(new NotificationStatus(updateSchedulePayloadDTO.getNotificationStatusId()));
        }

        if (schedule.getScheduleStatus() != null && schedule.getScheduleStatus().getId() == null) {
            schedule.setScheduleStatus(null);
        } else if (updateSchedulePayloadDTO.getScheduleStatusId() != null) {
            schedule.setScheduleStatus(new ScheduleStatus(updateSchedulePayloadDTO.getScheduleStatusId()));
        }

        return schedule;
    }
}
