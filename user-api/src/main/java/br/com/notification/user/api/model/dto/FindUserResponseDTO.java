package br.com.notification.user.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindUserResponseDTO {
    private Integer id;
    private String name;
    private String email;
    private Boolean optOut;
}
