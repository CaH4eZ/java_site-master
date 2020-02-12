package ru.ugrasu.journal.model.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ugrasu.journal.model.entities.UserEntity;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    @Query("select u from UserEntity u")
    public List<UserEntity> findAll();

//    @Modifying
//    @Transactional
//    @Query("delete from UserEntity u where u.id = :id")
//    public void deleteById(@Param("id") int id);
//
//    @Modifying
//    @Transactional
//    @Query("update UserEntity u set u.name = :name where u.id = :id")
//    public void updateById(@Param("id") int id, @Param("name") String name);
//
//    @Modifying
//    @Transactional
//    @Query(value = "insert into user values(:id,:name,:role,:group)", nativeQuery = true)
//    public void save1(@Param("id") int id, @Param("name") String name,
//                     @Param("role") int role, @Param("group") int group);
}