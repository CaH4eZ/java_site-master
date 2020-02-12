package ru.ugrasu.journal.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ugrasu.journal.model.repositories.ExcerciseRepository;
import ru.ugrasu.journal.model.repositories.InstituteRepository;

@Component
public class InstituteService {

    @Autowired
    private InstituteRepository instituteRepository;

}
