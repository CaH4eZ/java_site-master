package ru.ugrasu.journal.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
public class StudyGroupDto {
    private int id;
    private String name;
    private DepartmentDto departmentByDepartmentId;
    private List<UserDto> usersById;

}
