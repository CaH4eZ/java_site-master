package ru.ugrasu.journal.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ugrasu.journal.exception.NotFoundException;
import ru.ugrasu.journal.model.entities.SubjectEntity;
import ru.ugrasu.journal.model.repositories.StudyGroupRepository;
import ru.ugrasu.journal.model.repositories.SubjectRepository;

import java.util.List;

@Component
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<SubjectEntity> findAll() {
        return subjectRepository.findAll();
    }

    public void delete(SubjectEntity subjectEntity) {
        subjectRepository.delete(subjectEntity);
    }

    public SubjectEntity findById(int id) {
        SubjectEntity subjectEntity = subjectRepository.findOne(id);

        if (subjectEntity == null) {
            throw new NotFoundException("Не найден предмет");
        }
        else {
            return subjectEntity;
        }
    }

}
