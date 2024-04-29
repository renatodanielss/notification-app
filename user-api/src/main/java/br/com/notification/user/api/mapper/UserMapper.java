package br.com.notification.user.api.mapper;

import br.com.notification.user.api.model.User;
import br.com.notification.user.api.model.dto.CreateAndUpdateUserResponseDTO;
import br.com.notification.user.api.model.dto.CreateUserPayloadDTO;
import br.com.notification.user.api.model.dto.UpdateUserPayloadDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToEntity(CreateUserPayloadDTO createUserPayloadDTO);

    CreateAndUpdateUserResponseDTO entityToDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void dtoToEntityIgnoreNullValues(@MappingTarget User user, UpdateUserPayloadDTO updateUserPayloadDTO);

}
