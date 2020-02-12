package ru.ugrasu.journal.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ugrasu.journal.exception.NotFoundException;
import ru.ugrasu.journal.model.entities.UserEntity;
import ru.ugrasu.journal.model.repositories.UserExcerciseRepository;
import ru.ugrasu.journal.model.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public List<UserEntity> findUserByGroupId(int id) {
        List<UserEntity> listUserEntity = findAll();
        List<UserEntity> newListUserEntity = new ArrayList<>();

        listUserEntity.forEach(userEntity -> {
            //Проверка роли (студент или староста)
            String role = userEntity.getRoleByRole().getName();

            if (role.equals("Староста") || role.equals("Студент")) {

                //Проверка группы (ТОЛЬКО У СТУДЕНТОВ)
                int groupId = userEntity.getStudyGroupByStudyGroup().getId();

                if (id == groupId) {
                    newListUserEntity.add(userEntity);
                }
            }
        });

        return newListUserEntity;
    }

    public UserEntity findById(int id) {
        UserEntity userEntity = userRepository.findOne(id);

        if (userEntity == null) {
            throw new NotFoundException("Пользователь не найден");
        }
        else {
            return userEntity;
        }
    }

    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public void delete(UserEntity userEntity) {
        userRepository.delete(userEntity);
    }

}
