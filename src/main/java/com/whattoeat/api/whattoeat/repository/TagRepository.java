package com.whattoeat.api.whattoeat.repository;

import com.whattoeat.api.whattoeat.domain.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, String> {
}
