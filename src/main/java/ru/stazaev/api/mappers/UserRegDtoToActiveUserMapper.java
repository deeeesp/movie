package ru.stazaev.api.mappers;

import org.springframework.stereotype.Component;
import ru.stazaev.api.dto.request.UserRegistrationDto;
import ru.stazaev.store.entitys.Role;
import ru.stazaev.store.entitys.Status;
import ru.stazaev.store.entitys.User;

@Component
public class UserRegDtoToActiveUserMapper {
    public User dtoToEntity(UserRegistrationDto userDto){
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        return user;
    }
}
