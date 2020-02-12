package ru.ugrasu.journal.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ugrasu.journal.model.entities.UserExcerciseEntity;

import java.util.List;

@Repository
public interface UserExcerciseRepository extends CrudRepository<UserExcerciseEntity, Integer> {

        @Query("select ue from UserExcerciseEntity ue")
        public List<UserExcerciseEntity> findAll();

        @Query("select ue from UserExcerciseEntity ue where ue.userByUser.id = :u and ue.excerciseByExcercise.id = :e")
        public UserExcerciseEntity findByUE(@Param("u") int u, @Param("e") int e);

}
