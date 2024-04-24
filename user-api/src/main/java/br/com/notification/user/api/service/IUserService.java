package br.com.notification.user.api.service;

import br.com.notification.user.api.model.dto.CreateUserPayloadDTO;
import br.com.notification.user.api.model.dto.CreateUserResponseDTO;
import br.com.notification.user.api.model.dto.FindUserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface IUserService {

    CreateUserResponseDTO save(CreateUserPayloadDTO createUserPayloadDTO);

    List<FindUserResponseDTO> findAllByNameAndEmail(String name, String email);
}
