package cl.smartjob.example.service.user.mapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import cl.smartjob.example.service.user.dto.PhoneDTO;
import cl.smartjob.example.service.user.dto.UserResponseDTO;
import cl.smartjob.example.service.user.entity.Phone;
import cl.smartjob.example.service.user.entity.User;

/**
 * Mapper class to convert Entities to DTOs
 */
@Component
public class DtoUserMapper {

    /**
     * Convert User entity to UserResponseDTO
     * 
     * @param user the entity to convert
     * @return UserResponseDTO
     */
    public UserResponseDTO toResponseDTO(User user) {
        List<PhoneDTO> phoneDTOs = user.getPhones().stream()
            .map(this::toPhoneDTO)
            .collect(Collectors.toList());

        return UserResponseDTO.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .phones(phoneDTOs)
            .created(user.getCreated())
            .modified(user.getModified())
            .last_login(user.getLastLogin())
            .isactive(user.getIsactive())
            .build();
    }

    /**
     * Convert Phone entity to PhoneDTO
     * 
     * @param phone the entity to convert
     * @return PhoneDTO
     */
    public PhoneDTO toPhoneDTO(Phone phone) {

        return PhoneDTO.builder()
            .number(phone.getNumber())
            .citycode(phone.getCitycode())
            .contrycode(phone.getContrycode())
            .build();
    }
}
