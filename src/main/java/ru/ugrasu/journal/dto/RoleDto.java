package ru.ugrasu.journal.dto;

import lombok.Data;
import ru.ugrasu.journal.model.entities.UserEntity;

import java.util.List;

@Data
public class RoleDto {
    private int id;
    private String name;
    private List<UserDto> usersById;
}
