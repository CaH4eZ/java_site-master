package ru.ugrasu.journal.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ugrasu.journal.model.entities.RoleEntity;
import ru.ugrasu.journal.model.entities.SubjectEntity;

import java.util.List;

@Repository
public interface SubjectRepository extends CrudRepository<SubjectEntity, Integer> {

    @Query("select s from SubjectEntity s")
    public List<SubjectEntity> findAll();

}
