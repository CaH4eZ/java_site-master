package ru.ugrasu.journal.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ugrasu.journal.model.entities.ExcerciseEntity;

import java.util.List;

@Repository
public interface ExcerciseRepository extends CrudRepository<ExcerciseEntity, Integer> {

    @Query("select e from ExcerciseEntity e")
    public List<ExcerciseEntity> findAll();

}
