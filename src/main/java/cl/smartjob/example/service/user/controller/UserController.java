package cl.smartjob.example.service.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.smartjob.example.service.user.dto.UserRequestDTO;
import cl.smartjob.example.service.user.dto.UserResponseDTO;
import cl.smartjob.example.service.user.service.UserService;
import jakarta.validation.Valid;

/**
 * UserController class to handle user-related HTTP requests.
 * Provides an endpoint for user registration.
 * 
 * @author Carlos Icaza
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Register a new user
     * 
     * @param userRequestDTO user registration data
     * @return registered user response
     */
    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO>
        registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponse = userService.registerUser(userRequestDTO);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
}
