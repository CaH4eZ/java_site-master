package ru.ugrasu.journal.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
public class DepartmentDto {
    private int id;
    private String name;
    private InstituteDto instituteByInstituteId;
    private List<StudyGroupDto> studyGroupsById;
    private List<UserDto> usersById;
}
