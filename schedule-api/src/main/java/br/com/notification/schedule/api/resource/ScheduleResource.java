package br.com.notification.schedule.api.resource;

import br.com.notification.schedule.api.config.annotation.SortRequestParam;
import br.com.notification.schedule.api.model.dto.CreateAndUpdateScheduleResponseDTO;
import br.com.notification.schedule.api.model.dto.CreateSchedulePayloadDTO;
import br.com.notification.schedule.api.model.dto.FindScheduleResponseDTO;
import br.com.notification.schedule.api.model.dto.UpdateSchedulePayloadDTO;
import br.com.notification.schedule.api.service.IScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@ApiResponses(value= {
		@ApiResponse(responseCode = "200", description = "Return ResponseModel with success message"),
		@ApiResponse(responseCode = "500", description = "Case has error, return a ResponseModel with Exception")
})
@RequestMapping("/schedules")
public class ScheduleResource {

	private final IScheduleService scheduleService;

	@Autowired
	public ScheduleResource(IScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@PostMapping
	@Operation(
			summary = "Creates a schedule",
			description = "This operation creates a schedule"
	)
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Created"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@SecurityRequirement(name = "basicAuth")
	public @ResponseBody ResponseEntity<CreateAndUpdateScheduleResponseDTO> save(@RequestBody @Valid CreateSchedulePayloadDTO createSchedulePayloadDTO) {
		return new ResponseEntity<>(this.scheduleService.save(createSchedulePayloadDTO), HttpStatus.CREATED);
	}

	@PatchMapping("/{id}")
	@Operation(
			summary = "Updates a schedule",
			description = "This operation Updates a schedule"
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@SecurityRequirement(name = "basicAuth")
	public @ResponseBody ResponseEntity<CreateAndUpdateScheduleResponseDTO> partialUpdate(@PathVariable("id") Integer id,
																					   @RequestBody @Valid UpdateSchedulePayloadDTO updateSchedulePayloadDTO) {
		return new ResponseEntity<>(this.scheduleService.partialUpdate(id, updateSchedulePayloadDTO), HttpStatus.OK);
	}

	@GetMapping
	@Operation(
			summary = "Finds schedules",
			description = "This operation finds a schedules"
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@SecurityRequirement(name = "basicAuth")
	public @ResponseBody ResponseEntity<Page<FindScheduleResponseDTO>> findAllByUserIdAndSearch(
											@RequestParam(name = "userId", required = false) Integer userId,
											@RequestParam(name = "search", defaultValue = "") Optional<String> search,
											@ParameterObject Pageable pageable,
											@SortRequestParam Sort sort) {
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		return new ResponseEntity<>(this.scheduleService.findAllByUserIdAndSearch(userId, search.orElse(""), pageRequest), HttpStatus.OK);
	}
}
