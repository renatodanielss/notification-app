package br.com.notification.user.api.mapper;

import br.com.notification.user.api.model.User;
import br.com.notification.user.api.model.dto.CreateUserPayloadDTO;
import br.com.notification.user.api.model.dto.CreateUserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToEntity(CreateUserPayloadDTO createUserPayloadDTO);

    CreateUserResponseDTO entityToDto(User user);

}
