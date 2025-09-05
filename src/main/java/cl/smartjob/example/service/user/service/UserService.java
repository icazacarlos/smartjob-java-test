package cl.smartjob.example.service.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.smartjob.example.service.user.dto.UserRequestDTO;
import cl.smartjob.example.service.user.dto.UserResponseDTO;
import cl.smartjob.example.service.user.entity.User;
import cl.smartjob.example.service.user.exception.EmailAlreadyExistsException;
import cl.smartjob.example.service.user.exception.ValidationException;
import cl.smartjob.example.service.user.mapper.DtoUserMapper;
import cl.smartjob.example.service.user.mapper.EntityUserMapper;
import cl.smartjob.example.service.user.repository.UserRepository;
import cl.smartjob.example.service.user.util.ValidationUtil;

/**
 * Service class for user operations
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityUserMapper entityUserMapper;

    @Autowired
    private DtoUserMapper dtoUserMapper;

    @Autowired
    private ValidationUtil validationUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a new user
     * 
     * @param userRequestDTO user registration data
     * @return registered user response
     * @throws EmailAlreadyExistsException if email already exists
     * @throws ValidationException if validation fails
     */
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        // Validate email format
        if (!validationUtil.isValidEmail(userRequestDTO.getEmail())) {
            throw new ValidationException(validationUtil.getEmailMessage());
        }

        // Validate password format
        if (!validationUtil.isValidPassword(userRequestDTO.getPassword())) {
            throw new ValidationException(validationUtil.getPasswordMessage());
        }

        // Check if email already exists
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(validationUtil.getUserMessage());
        }

        // Encode password
        String encodedPassword = passwordEncoder.encode(userRequestDTO.getPassword());

        // Convert DTO to entity
        User user = entityUserMapper.toUser(userRequestDTO, encodedPassword);

        // Save user
        User savedUser = userRepository.save(user);

        // Convert entity to response DTO
        return dtoUserMapper.toResponseDTO(savedUser);
    }
}
