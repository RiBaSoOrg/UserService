package com.ribaso.userservice.core.domain.service.interfaces;

import com.ribaso.userservice.core.domain.model.User;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {

}
