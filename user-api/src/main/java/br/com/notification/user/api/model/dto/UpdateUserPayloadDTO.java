package br.com.notification.user.api.model.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserPayloadDTO {
    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String email;

    private Boolean optOut;
}
