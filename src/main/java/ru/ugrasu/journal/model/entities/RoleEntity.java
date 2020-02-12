package ru.ugrasu.journal.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "role", schema = "journal", catalog = "")
public class RoleEntity {
    @Id@Column(name = "id")
    private int id;
    @Basic@Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "roleByRole")
    private List<UserEntity> usersById;

}
