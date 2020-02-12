package ru.ugrasu.journal.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private int id;
    private String name;
    private String password;
    private List<SubjectDto> subjectsById;
    private RoleDto roleByRole;
    private StudyGroupDto studyGroupByStudyGroup;
    private DepartmentDto departmentByDepartment;

    //Для сохранения - принимаем int
    private int roleId;
    private int studyGroupId;
    private int departmentId;

    //Будем вытаскивать через промежуточную сущность сразу список занятий
    private List<ExcerciseDto> ExcercisesById;
}
