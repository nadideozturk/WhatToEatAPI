package com.whattoeat.api.whattoeat.controllers;

import com.google.common.base.Strings;
import com.whattoeat.api.whattoeat.domain.User;
import com.whattoeat.api.whattoeat.dto.HomeMadeMealDTO;
import com.whattoeat.api.whattoeat.dto.OutsideMealDTO;
import com.whattoeat.api.whattoeat.mapper.HomeMadeMealMapper;
import com.whattoeat.api.whattoeat.mapper.OutsideMealMapper;
import com.whattoeat.api.whattoeat.repository.HomeMadeMealRepository;
import com.whattoeat.api.whattoeat.repository.OutsideMealRepository;
import com.whattoeat.api.whattoeat.repository.UserRepository;
import com.whattoeat.api.whattoeat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/explore")
@RestController
public class ExploreController {

    private static final int TOP_HOMEMADE_MEALS = 30;

    private static final int TOP_OUTSIDE_MEALS = 30;

    private static final String ERR_MSG_CITY_COUNTRY_NOT_SELECTED =
            "User should have city or country selected prior using explore feature. userId=[%s]";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private HomeMadeMealRepository homeMadeMealRepository;

    @Autowired
    private OutsideMealRepository outsideMealRepository;

    @Autowired
    private HomeMadeMealMapper homeMadeMealMapper;

    @Autowired
    private OutsideMealMapper outsideMealMapper;

    @RequestMapping(value = "/homemade", method = RequestMethod.GET)
    public List<HomeMadeMealDTO> exploreHomemade() {
        final String userId = userService.getUserID();
        final User user = userRepository.findOne(userId);
        final String country = user.getCountry();
        final String city = user.getCity();
        if (Strings.isNullOrEmpty(country) || Strings.isNullOrEmpty(city)) {
            throw new IllegalArgumentException(String.format(ERR_MSG_CITY_COUNTRY_NOT_SELECTED, userId));
        }
        return homeMadeMealRepository.findExplore(country, city, TOP_HOMEMADE_MEALS)
                .stream()
                .map(homeMadeMealMapper::toDTO)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/outside", method = RequestMethod.GET)
    public List<OutsideMealDTO> exploreOutside() {
        final String userId = userService.getUserID();
        final User user = userRepository.findOne(userId);
        final String country = user.getCountry();
        final String city = user.getCity();
        if (Strings.isNullOrEmpty(country) || Strings.isNullOrEmpty(city)) {
            throw new IllegalArgumentException(String.format(ERR_MSG_CITY_COUNTRY_NOT_SELECTED, userId));
        }
        return outsideMealRepository.findExplore(country, city, TOP_OUTSIDE_MEALS)
                .stream()
                .map(outsideMealMapper::toDTO)
                .collect(Collectors.toList());
    }

}
