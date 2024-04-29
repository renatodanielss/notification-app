package br.com.notification.user.api.service;

import br.com.notification.user.api.mapper.UserMapper;
import br.com.notification.user.api.model.User;
import br.com.notification.user.api.model.dto.CreateAndUpdateUserResponseDTO;
import br.com.notification.user.api.model.dto.CreateUserPayloadDTO;
import br.com.notification.user.api.model.dto.FindUserResponseDTO;
import br.com.notification.user.api.model.dto.UpdateUserPayloadDTO;
import br.com.notification.user.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
@SuppressWarnings("unused")
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public CreateAndUpdateUserResponseDTO save(CreateUserPayloadDTO createUserPayloadDTO) {
        User user = this.userMapper.dtoToEntity(createUserPayloadDTO);
        user = this.userRepository.save(user);

        return this.userMapper.entityToDto(user);
    }

    @Override
    @Transactional
    public CreateAndUpdateUserResponseDTO partialUpdate(Integer userId, UpdateUserPayloadDTO updateUserPayloadDTO) {
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("Entity User not found!");
        }
        User user = userOptional.get();

        this.userMapper.dtoToEntityIgnoreNullValues(user, updateUserPayloadDTO);
        this.userRepository.save(user);

        return this.userMapper.entityToDto(user);
    }

    @Override
    @Transactional
    public List<FindUserResponseDTO> findAllByNameAndEmail(String name, String email) {
        return this.userRepository.findAllByNameAndEmail(name, email);
    }
}
