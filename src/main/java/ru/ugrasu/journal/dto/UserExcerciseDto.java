package ru.ugrasu.journal.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class UserExcerciseDto {
    private int id;
    private UserDto userByUser;
    private ExcerciseDto excerciseByExcercise;
    private int userId;
    private int excerciseId;

}
