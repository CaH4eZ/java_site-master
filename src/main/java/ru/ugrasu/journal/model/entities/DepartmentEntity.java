package ru.ugrasu.journal.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "department", schema = "journal", catalog = "")
public class DepartmentEntity {
    @Id@Column(name = "id")
    private int id;
    @Basic@Column(name = "name")
    private String name;
    @ManyToOne@JoinColumn(name = "institute_id", referencedColumnName = "id", nullable = false)
    private InstituteEntity instituteByInstituteId;
    @OneToMany(mappedBy = "departmentByDepartmentId")
    private List<StudyGroupEntity> studyGroupsById;
    @OneToMany(mappedBy = "departmentByDepartment")
    private List<UserEntity> usersById;

}
