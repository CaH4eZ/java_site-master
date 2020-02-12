package ru.ugrasu.journal.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ugrasu.journal.exception.NotFoundException;
import ru.ugrasu.journal.model.entities.StudyGroupEntity;
import ru.ugrasu.journal.model.repositories.InstituteRepository;
import ru.ugrasu.journal.model.repositories.StudyGroupRepository;

import java.util.List;

@Component
public class StudyGroupService {

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    public List<StudyGroupEntity> findAll() {
        return studyGroupRepository.findAll();
    }

    public StudyGroupEntity findById(int id) {
        StudyGroupEntity studyGroupEntity = studyGroupRepository.findOne(id);

        if (studyGroupEntity == null) {
            throw new NotFoundException("Не найдена группа");
        }
        else {
            return studyGroupEntity;
        }
    }

}
