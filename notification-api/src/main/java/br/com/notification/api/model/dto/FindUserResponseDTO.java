package br.com.notification.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindUserResponseDTO {
    private Integer id;
    private Boolean optOut;
}
