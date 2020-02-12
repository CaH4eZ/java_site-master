package ru.ugrasu.journal.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "user", schema = "journal", catalog = "")
public class UserEntity {
    @Id@Column(name = "id")
    private int id;
    @Basic@Column(name = "name")
    private String name;
    @Basic@Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "userByTeacher")
    private List<SubjectEntity> subjectsById;
    @ManyToOne@JoinColumn(name = "role", referencedColumnName = "id", nullable = false)
    private RoleEntity roleByRole;
    @ManyToOne@JoinColumn(name = "study_group", referencedColumnName = "id")
    private StudyGroupEntity studyGroupByStudyGroup;
    @ManyToOne@JoinColumn(name = "department", referencedColumnName = "id")
    private DepartmentEntity departmentByDepartment;
    @OneToMany(mappedBy = "userByUser")
    private List<UserExcerciseEntity> userExcercisesById;

}
