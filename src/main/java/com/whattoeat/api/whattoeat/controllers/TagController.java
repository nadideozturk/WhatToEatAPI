package com.whattoeat.api.whattoeat.controllers;

import com.whattoeat.api.whattoeat.domain.Tag;
import com.whattoeat.api.whattoeat.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/tags")
@RestController
public class TagController {

    @Autowired
    private TagRepository repository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Tag> getAll() {
        return (List<Tag>) repository.findAll();
    }

}
