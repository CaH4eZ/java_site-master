package ru.ugrasu.journal.model.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "excercise", schema = "journal", catalog = "")
public class ExcerciseEntity {
    @Id@Column(name = "id")
    private int id;
    @Basic@Column(name = "date")
    private Date date;
    @Basic@Column(name = "group_id")
    private int groupId;
    @ManyToOne@JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    private SubjectEntity subjectBySubjectId;
    @OneToMany(mappedBy = "excerciseByExcercise")
    private List<UserExcerciseEntity> userExcercisesById;

}
