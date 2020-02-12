package ru.ugrasu.journal.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "study_group", schema = "journal", catalog = "")
public class StudyGroupEntity {
    @Id@Column(name = "id")
    private int id;
    @Basic@Column(name = "name")
    private String name;
    @ManyToOne@JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false)
    private DepartmentEntity departmentByDepartmentId;
    @OneToMany(mappedBy = "studyGroupByStudyGroup")
    private List<UserEntity> usersById;

}
