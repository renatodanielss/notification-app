package br.com.notification.user.api.service.user;

import lombok.Data;

@Data
public class CreateUserResponseDTO {
    private Integer id;
    private String name;
    private String email;
    private Boolean optOut;
}
