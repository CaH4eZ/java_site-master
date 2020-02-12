package ru.ugrasu.journal.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
public class SubjectDto {
    private int id;
    private String name;
    private List<ExcerciseDto> excercisesById;
    private UserDto userByTeacher;

}
