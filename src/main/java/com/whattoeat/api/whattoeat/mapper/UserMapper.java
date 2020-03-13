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
                .build();
    }

    public User fromDTO(UserDTO userDTO, String userId){
        return User.builder()
                .id(userId)
                .isPrivate(userDTO.isPrivate())
                .build();
    }
}
