package br.com.notification.user.api.service;

import br.com.notification.user.api.model.dto.CreateAndUpdateUserResponseDTO;
import br.com.notification.user.api.model.dto.CreateUserPayloadDTO;
import br.com.notification.user.api.model.dto.FindUserResponseDTO;
import br.com.notification.user.api.model.dto.UpdateUserPayloadDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface IUserService {

    CreateAndUpdateUserResponseDTO save(CreateUserPayloadDTO createUserPayloadDTO);

    CreateAndUpdateUserResponseDTO partialUpdate(Integer userId, UpdateUserPayloadDTO updateUserPayloadDTO);

    List<FindUserResponseDTO> findAllByNameAndEmail(String name, String email);
}
