package br.com.notification.user.api.service.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserPayloadDTO {
    @Size(max = 100)
    @NotNull
    private String name;

    @Size(max = 100)
    @NotNull
    private String email;

    private Boolean optOut;
}
