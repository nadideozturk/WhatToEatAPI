package com.whattoeat.api.whattoeat.repository;

import com.whattoeat.api.whattoeat.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
