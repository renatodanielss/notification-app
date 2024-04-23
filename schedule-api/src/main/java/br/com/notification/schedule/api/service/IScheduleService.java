package br.com.notification.schedule.api.service;

import br.com.notification.schedule.api.model.dto.CreateAndUpdateScheduleResponseDTO;
import br.com.notification.schedule.api.model.dto.CreateSchedulePayloadDTO;
import br.com.notification.schedule.api.model.dto.FindScheduleResponseDTO;
import br.com.notification.schedule.api.model.dto.UpdateSchedulePayloadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public interface IScheduleService {

    CreateAndUpdateScheduleResponseDTO save(CreateSchedulePayloadDTO createSchedulePayloadDTO);

    CreateAndUpdateScheduleResponseDTO partialUpdate(Integer scheduleId, UpdateSchedulePayloadDTO updateSchedulePayloadDTO);

    Page<FindScheduleResponseDTO> findAllByUserIdAndSearch(Integer userId, String search, Pageable pageable);
}
