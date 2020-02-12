package ru.ugrasu.journal.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ugrasu.journal.model.entities.DepartmentEntity;

import java.util.List;

@Repository
public interface DepartmentRepository extends CrudRepository<DepartmentEntity, Integer> {

    @Query("select d from DepartmentEntity d")
    public List<DepartmentEntity> findAll();

}