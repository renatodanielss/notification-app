package br.com.notification.user.api.resource;

import br.com.notification.user.api.model.dto.CreateAndUpdateUserResponseDTO;
import br.com.notification.user.api.model.dto.CreateUserPayloadDTO;
import br.com.notification.user.api.model.dto.FindUserResponseDTO;
import br.com.notification.user.api.model.dto.UpdateUserPayloadDTO;
import br.com.notification.user.api.service.IUserService;
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
@RequestMapping("/users")
public class UserResource {

	private final IUserService userService;

	@Autowired
	public UserResource(IUserService userService) {
		this.userService = userService;
	}

	@PostMapping
	@Operation(
			summary = "Creates an user",
			description = "This operation creates an user"
	)
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Created"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@SecurityRequirement(name = "basicAuth")
	public @ResponseBody ResponseEntity<CreateAndUpdateUserResponseDTO> save(@RequestBody @Valid CreateUserPayloadDTO createUserPayloadDTO) {
		return new ResponseEntity<>(this.userService.save(createUserPayloadDTO), HttpStatus.CREATED);
	}

	@PatchMapping("/{id}")
	@Operation(
			summary = "Updates an user",
			description = "This operation Updates an user"
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@SecurityRequirement(name = "basicAuth")
	public @ResponseBody ResponseEntity<CreateAndUpdateUserResponseDTO> partialUpdate(@PathVariable("id") Integer id,
																							  @RequestBody @Valid UpdateUserPayloadDTO updateUserPayloadDTO) {
		return new ResponseEntity<>(this.userService.partialUpdate(id, updateUserPayloadDTO), HttpStatus.OK);
	}

	@GetMapping
	@Operation(
			summary = "Finds user by name and email",
			description = "This operation finds user by name and email"
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	public @ResponseBody ResponseEntity<List<FindUserResponseDTO>> findAllByNameAndEmail(@RequestParam(value = "name", required = false) String name,
																		@RequestParam(value = "email", required = false) String email) {
		return new ResponseEntity<>(this.userService.findAllByNameAndEmail(name, email), HttpStatus.OK);
	}
}
