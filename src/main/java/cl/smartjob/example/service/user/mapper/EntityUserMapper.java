package cl.smartjob.example.service.user.mapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import cl.smartjob.example.service.user.dto.PhoneDTO;
import cl.smartjob.example.service.user.dto.UserRequestDTO;
import cl.smartjob.example.service.user.entity.Phone;
import cl.smartjob.example.service.user.entity.User;

/**
 * Mapper class to convert DTOs to Entities
 */
@Component
public class EntityUserMapper {

    /**
     * Convert UserRequestDTO to User entity
     * 
     * @param userRequestDTO the DTO to convert
     * @param encodedPassword the encoded password
     * @return User entity
     */
    public User toUser(UserRequestDTO userRequestDTO, String encodedPassword) {
        User user = new User(
            userRequestDTO.getName(),
            userRequestDTO.getEmail(),
            encodedPassword);

        List<Phone> phones = userRequestDTO.getPhones().stream()
            .map(this::toPhone)
            .collect(Collectors.toList());

        user.setPhones(phones);
        return user;
    }

    /**
     * Convert PhoneDTO to Phone entity
     * 
     * @param phoneDTO the DTO to convert
     * @return Phone entity
     */
    public Phone toPhone(PhoneDTO phoneDTO) {
        return new Phone(
            phoneDTO.getNumber(),
            phoneDTO.getCitycode(),
            phoneDTO.getContrycode());
    }
}
