package cl.smartjob.example.service.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.UUID;

import cl.smartjob.example.service.user.dto.PhoneDTO;
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
 * Unit tests for UserService. Covers user registration scenarios including success and various
 * failure cases. Uses Mockito for mocking dependencies and JUnit 5 for assertions. Ensures robust
 * validation and error handling in user registration logic.
 * 
 * @author Carlos Icaza
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EntityUserMapper entityUserMapper;

    @Mock
    private DtoUserMapper dtoUserMapper;

    @Mock
    private ValidationUtil validationUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserRequestDTO userRequestDTO;
    private User user;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        PhoneDTO phoneDTO = new PhoneDTO("1234567", "1", "57");
        userRequestDTO = new UserRequestDTO("Juan Rodriguez", "juan@rodriguez.org", "Password123!",
            Arrays.asList(phoneDTO));

        user = new User("Juan Rodriguez", "juan@rodriguez.org", "encodedPassword");
        user.setId(UUID.randomUUID());

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName("Juan Rodriguez");
        userResponseDTO.setEmail("juan@rodriguez.org");
    }

    @Test
    void registerUser_Success() {
        when(validationUtil.isValidEmail(anyString())).thenReturn(true);
        when(validationUtil.isValidPassword(anyString())).thenReturn(true);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(entityUserMapper.toUser(any(UserRequestDTO.class), anyString())).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(dtoUserMapper.toResponseDTO(any(User.class))).thenReturn(userResponseDTO);

        UserResponseDTO result = userService.registerUser(userRequestDTO);

        assertNotNull(result);
        assertEquals("Juan Rodriguez", result.getName());
        assertEquals("juan@rodriguez.org", result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUser_InvalidEmail_ThrowsValidationException() {
        when(validationUtil.isValidEmail(anyString())).thenReturn(false);
        when(validationUtil.getEmailMessage()).thenReturn("Invalid email format");

        assertThrows(ValidationException.class,
            () -> userService.registerUser(userRequestDTO));
    }

    @Test
    void registerUser_InvalidPassword_ThrowsValidationException() {
        when(validationUtil.isValidEmail(anyString())).thenReturn(true);
        when(validationUtil.isValidPassword(anyString())).thenReturn(false);
        when(validationUtil.getPasswordMessage()).thenReturn("Invalid password format");

        assertThrows(ValidationException.class,
            () -> userService.registerUser(userRequestDTO));
    }

    @Test
    void registerUser_EmailAlreadyExists_ThrowsEmailAlreadyExistsException() {
        when(validationUtil.isValidEmail(anyString())).thenReturn(true);
        when(validationUtil.isValidPassword(anyString())).thenReturn(true);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class,
            () -> userService.registerUser(userRequestDTO));
    }
}
