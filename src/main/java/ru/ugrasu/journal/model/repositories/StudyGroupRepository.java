package ru.ugrasu.journal.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ugrasu.journal.model.entities.StudyGroupEntity;

import java.util.List;

@Repository
public interface StudyGroupRepository extends CrudRepository<StudyGroupEntity, Integer> {

    @Query("select sg from StudyGroupEntity sg")
    public List<StudyGroupEntity> findAll();

}
