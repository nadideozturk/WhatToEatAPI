package com.whattoeat.api.whattoeat.controllers;

import com.google.common.base.Strings;
import com.whattoeat.api.whattoeat.domain.User;
import com.whattoeat.api.whattoeat.dto.UserDTO;
import com.whattoeat.api.whattoeat.exception.NotFoundException;
import com.whattoeat.api.whattoeat.mapper.UserMapper;
import com.whattoeat.api.whattoeat.repository.HomeMadeMealRepository;
import com.whattoeat.api.whattoeat.repository.OutsideMealRepository;
import com.whattoeat.api.whattoeat.repository.UserRepository;
import com.whattoeat.api.whattoeat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HomeMadeMealRepository homeMadeMealRepository;

    @Autowired
    private OutsideMealRepository outsideMealRepository;

    @Autowired
    private UserMapper mapper;

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public UserDTO getUserDetails() {
        UserDTO userDTO = userService.getUser();
        if (userDTO == null) {
            throw new NotFoundException();
        }
        User user = userRepository.findOne(userDTO.getId());
        if (user == null) {
            userDTO.setPrivate(false);
        } else {
            // TODO refactor to mapper
            userDTO.setPrivate(user.isPrivate());
            userDTO.setCountry(user.getCountry());
            userDTO.setCity(user.getCity());
        }
        return userDTO;
    }

    @RequestMapping(value = "/preferences", method = RequestMethod.PUT)
    public UserDTO setUserPreferences(@RequestBody UserDTO userPreferences) {
        UserDTO userDto = userService.getUser();
        userDto.setPrivate(userPreferences.isPrivate());
        userDto.setCity(userPreferences.getCity());
        userDto.setCountry(userPreferences.getCountry());
        // TODO add new fields here for merging the fields between token userDTO and request body user DTO
        User user = mapper.fromDTO(userDto);
        userRepository.save(user);

        if (!Strings.isNullOrEmpty(userPreferences.getCity()) &&
                !Strings.isNullOrEmpty(userPreferences.getCountry())) {
            homeMadeMealRepository
                    .findByUserId(userDto.getId())
                    .stream()
                    .filter(m -> Strings.isNullOrEmpty(m.getCountry()) || Strings.isNullOrEmpty(m.getCity()))
                    .forEach(m -> {
                        m.setCountry(userPreferences.getCountry());
                        m.setCity(userPreferences.getCity());
                        homeMadeMealRepository.save(m);
                    });

            outsideMealRepository
                    .findByUserId(userDto.getId())
                    .stream()
                    .filter(m -> Strings.isNullOrEmpty(m.getCountry()) || Strings.isNullOrEmpty(m.getCity()))
                    .forEach(m -> {
                        m.setCountry(userPreferences.getCountry());
                        m.setCity(userPreferences.getCity());
                        outsideMealRepository.save(m);
                    });
        }

        return userDto;
    }

}
