package ru.ugrasu.journal.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "institute", schema = "journal", catalog = "")
public class InstituteEntity {
    @Id@Column(name = "id")
    private int id;
    @Basic@Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "instituteByInstituteId")
    private List<DepartmentEntity> departmentsById;

}
