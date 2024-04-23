package br.com.notification.schedule.api.service;

import br.com.notification.schedule.api.mapper.NotificationMapper;
import br.com.notification.schedule.api.mapper.ScheduleMapper;
import br.com.notification.schedule.api.model.*;
import br.com.notification.schedule.api.model.dto.CreateAndUpdateScheduleResponseDTO;
import br.com.notification.schedule.api.model.dto.CreateSchedulePayloadDTO;
import br.com.notification.schedule.api.model.dto.FindScheduleResponseDTO;
import br.com.notification.schedule.api.model.dto.UpdateSchedulePayloadDTO;
import br.com.notification.schedule.api.repository.NotificationRepository;
import br.com.notification.schedule.api.repository.ScheduleRepository;
import br.com.notification.schedule.api.repository.UserScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@EnableScheduling
@SuppressWarnings("unused")
public class ScheduleService implements IScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final NotificationRepository notificationRepository;
    private final UserScheduleRepository userScheduleRepository;

    private final ScheduleMapper scheduleMapper;
    private final NotificationMapper notificationMapper;

    private static final Logger logger = LogManager.getLogger(ScheduleService.class);

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, NotificationRepository notificationRepository,
                           UserScheduleRepository userScheduleRepository, ScheduleMapper scheduleMapper,
                           NotificationMapper notificationMapper) {
        this.scheduleRepository = scheduleRepository;
        this.notificationRepository = notificationRepository;
        this.userScheduleRepository = userScheduleRepository;
        this.scheduleMapper = scheduleMapper;
        this.notificationMapper = notificationMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreateAndUpdateScheduleResponseDTO save(CreateSchedulePayloadDTO createSchedulePayloadDTO) {
        if (createSchedulePayloadDTO.getScheduledTime().compareTo(new Date()) < 0) {
            throw new IllegalArgumentException("It's not possible to create a schedule with scheduledTime before current datetime.");
        }

        Schedule schedule = this.scheduleMapper.dtoToEntity(createSchedulePayloadDTO);
        final Schedule scheduleSaved = this.scheduleRepository.save(schedule);
        List<UserSchedule> userSchedules = new ArrayList<>();
        createSchedulePayloadDTO.getUserIds().stream().forEach(userId -> {
            UserSchedule userSchedule = new UserSchedule(new UserSchedulePK(userId, scheduleSaved.getId()));
            userSchedules.add(userSchedule);
        });
        this.userScheduleRepository.saveAll(userSchedules);
        CreateAndUpdateScheduleResponseDTO createAndUpdateScheduleResponseDTO = this.scheduleMapper.entityToDto(schedule);
        createAndUpdateScheduleResponseDTO.setUserIds(
                userSchedules.stream().map(us -> us.getPk().getUserId()).collect(Collectors.toList())
        );

        return createAndUpdateScheduleResponseDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreateAndUpdateScheduleResponseDTO partialUpdate(Integer scheduleId, UpdateSchedulePayloadDTO updateSchedulePayloadDTO) {
        if (updateSchedulePayloadDTO.getScheduledTime().compareTo(new Date()) < 0) {
            throw new IllegalArgumentException("It's not possible to create a schedule with scheduledTime before current datetime.");
        }

        Optional<Schedule> scheduleOptional = this.scheduleRepository.findById(scheduleId);
        if (scheduleOptional.isEmpty()) {
            throw new EntityNotFoundException("Entity Schedule not found!");
        }
        Schedule schedule = scheduleOptional.get();

        this.scheduleMapper.dtoToEntityIgnoreNullValues(schedule, updateSchedulePayloadDTO);
        final Schedule scheduleSaved = this.scheduleRepository.save(schedule);

        List<UserSchedule> userSchedules = new ArrayList<>();
        updateSchedulePayloadDTO.getUserIds().stream().forEach(userId -> {
            UserSchedule userSchedule = new UserSchedule(new UserSchedulePK(userId, scheduleSaved.getId()));
            userSchedules.add(userSchedule);
        });
        this.userScheduleRepository.deleteAllByScheduleIdAndUserIds(
                scheduleId,
                userSchedules.stream().map(us -> us.getPk().getUserId()).collect(Collectors.toList())
        );
        this.userScheduleRepository.saveAll(userSchedules);

        return this.scheduleMapper.entityToDto(schedule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Page<FindScheduleResponseDTO> findAllByUserIdAndSearch(Integer userId, String search, Pageable pageable) {
        return this.scheduleRepository.findAllByUserIdAndSearch(userId, search, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    protected List<Schedule> getSchedules() {
        Calendar calendarStartDate = Calendar.getInstance();
        calendarStartDate.add(Calendar.MINUTE, -5);
        Date startDate = calendarStartDate.getTime();

        Calendar calendarEndDate = Calendar.getInstance();
        calendarEndDate.add(Calendar.MINUTE, +5);
        Date endDate = calendarEndDate.getTime();

        return this.scheduleRepository.findAllByStatusIdAndScheduledTime(
                new ArrayList<>(List.of(ScheduleStatus.IN_PROGRESS, ScheduleStatus.FINISHED, ScheduleStatus.CANCELED)),
                startDate, endDate
        );
    }

    @Transactional(rollbackFor = Exception.class)
    protected List<Notification> saveScheduleStatusesAndAddNotifications(List<Schedule> schedules) {
        List<Notification> notifications = new ArrayList<>();
        for (Schedule schedule : schedules) {
            schedule.setScheduleStatus(new ScheduleStatus(ScheduleStatus.IN_PROGRESS));
            this.scheduleRepository.save(schedule);
            for (UserSchedule userSchedule : schedule.getUserSchedules()) {
                Notification notification = this.notificationMapper.scheduleToNotification(schedule);
                notification.setUser(new User(userSchedule.getPk().getUserId()));
                notifications.add(notification);
            }
        }
        return notifications;
    }

    @Transactional(rollbackFor = Exception.class)
    protected void createNotificationsAndSchedules(List<Notification> notifications, List<Schedule> schedules) {
        try {
            this.notificationRepository.saveAll(notifications);
            for (Schedule schedule : schedules) {
                schedule.setScheduleStatus(new ScheduleStatus(ScheduleStatus.FINISHED));
                this.scheduleRepository.save(schedule);
            }
        } catch (Exception e) {
            for (Schedule schedule : schedules) {
                schedule.setScheduleStatus(new ScheduleStatus(ScheduleStatus.ERROR));
                this.scheduleRepository.save(schedule);
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    public void sendScheduledNotifications() {
        logger.info("Starting schedule verification...");
        List<Schedule> schedules = getSchedules();
        logger.info(String.format("%d schedules found", schedules.size()));

        List<Notification> notifications = saveScheduleStatusesAndAddNotifications(schedules);
        createNotificationsAndSchedules(notifications, schedules);

        logger.info("Schedule process finished successfully!");
    }
}
