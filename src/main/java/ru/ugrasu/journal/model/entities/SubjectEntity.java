package ru.ugrasu.journal.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "subject", schema = "journal", catalog = "")
public class SubjectEntity {
    @Id@Column(name = "id")
    private int id;
    @Basic@Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "subjectBySubjectId")
    private List<ExcerciseEntity> excercisesById;
    @ManyToOne@JoinColumn(name = "teacher", referencedColumnName = "id", nullable = false)
    private UserEntity userByTeacher;

}
