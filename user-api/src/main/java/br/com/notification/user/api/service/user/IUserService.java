package br.com.notification.user.api.service.user;

import br.com.notification.user.api.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface IUserService {

    CreateUserResponseDTO save(CreateUserPayloadDTO createUserPayloadDTO);

    List<FindUserResponseDTO> findAllByNameAndEmail(String name, String email);
}
