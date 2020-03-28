package com.whattoeat.api.whattoeat.mapper;

import com.whattoeat.api.whattoeat.domain.User;
import com.whattoeat.api.whattoeat.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .isPrivate(user.isPrivate())
                .city(user.getCity())
                .country(user.getCountry())
                .build();
    }

    public User fromDTO(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getId())
                .isPrivate(userDTO.isPrivate())
                .city(userDTO.getCity())
                .country(userDTO.getCountry())
                .build();
    }
}
