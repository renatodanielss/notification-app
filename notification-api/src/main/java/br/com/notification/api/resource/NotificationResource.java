package br.com.notification.api.resource;

import br.com.notification.api.model.dto.CreateAndUpdateNotificationResponseDTO;
import br.com.notification.api.model.dto.CreateNotificationPayloadDTO;
import br.com.notification.api.model.dto.FindNotificationResponseDTO;
import br.com.notification.api.model.dto.UpdateNotificationPayloadDTO;
import br.com.notification.api.service.INotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@ApiResponses(value= {
		@ApiResponse(responseCode = "200", description = "Return ResponseModel with success message"),
		@ApiResponse(responseCode = "500", description = "Case has error, return a ResponseModel with Exception")
})
@RequestMapping("/notifications")
public class NotificationResource {

	private final INotificationService notificationService;

	@Autowired
	public NotificationResource(INotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@PostMapping
	@Operation(
			summary = "Creates a notification",
			description = "This operation creates a notification"
	)
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Created"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@SecurityRequirement(name = "basicAuth")
	public @ResponseBody ResponseEntity<CreateAndUpdateNotificationResponseDTO> save(@RequestBody @Valid CreateNotificationPayloadDTO createNotificationPayloadDTO) {
		return new ResponseEntity<>(this.notificationService.save(createNotificationPayloadDTO), HttpStatus.CREATED);
	}

	@PatchMapping("/{id}")
	@Operation(
			summary = "Updates a notification",
			description = "This operation Updates a notification"
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@SecurityRequirement(name = "basicAuth")
	public @ResponseBody ResponseEntity<CreateAndUpdateNotificationResponseDTO> partialUpdate(@PathVariable("id") Integer id,
																							  @RequestBody @Valid UpdateNotificationPayloadDTO updateNotificationPayloadDTO) {
		return new ResponseEntity<>(this.notificationService.partialUpdate(id, updateNotificationPayloadDTO), HttpStatus.OK);
	}

	@GetMapping
	@Operation(
			summary = "Finds a notification by user id",
			description = "This operation finds a notification by user id"
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@SecurityRequirement(name = "basicAuth")
	public @ResponseBody ResponseEntity<List<FindNotificationResponseDTO>> findAllByUserId(
						@RequestParam("userId") Integer userId,
						@RequestParam(value = "notificationStatusId", required = false) Integer notificationStatusId) {
		return new ResponseEntity<>(this.notificationService.findAllByUserId(userId, notificationStatusId), HttpStatus.OK);
	}
}
