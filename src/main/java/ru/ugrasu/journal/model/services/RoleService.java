package ru.ugrasu.journal.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ugrasu.journal.exception.NotFoundException;
import ru.ugrasu.journal.model.entities.RoleEntity;
import ru.ugrasu.journal.model.repositories.RoleRepository;

import java.util.List;

@Component
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    public RoleEntity findById(int id) {
        RoleEntity roleEntity = roleRepository.findOne(id);

        if (roleEntity == null) {
            throw new NotFoundException("Нету роли");
        }
        else {
            return roleEntity;
        }
    }
}
