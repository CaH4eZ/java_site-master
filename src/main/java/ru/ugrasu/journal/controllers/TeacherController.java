package ru.ugrasu.journal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.ugrasu.journal.dto.*;
import ru.ugrasu.journal.exception.NotFoundException;
import ru.ugrasu.journal.model.entities.*;
import ru.ugrasu.journal.model.services.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "teacher")
public class TeacherController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private StudyGroupService studyGroupService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExcerciseService excerciseService;

    @Autowired
    private UserExcerciseService userExcerciseService;

    @RequestMapping(value = "/refrashSubject", produces = APPLICATION_JSON_UTF8_VALUE, method = GET)
    public List<SubjectDto> findAllSubject() {
        System.out.println("TeacherController - findAllSubject");

        List<SubjectDto> listSubjectDto = new ArrayList<>();
        List<SubjectEntity> listSubjectEntity = subjectService.findAll();

        if (listSubjectEntity == null) {
            throw new NotFoundException("Нет предметов");
        }
        else {
            listSubjectEntity.forEach(subjectEntity -> {
                SubjectDto subjectDto = new SubjectDto();
                subjectDto.setId(subjectEntity.getId());
                subjectDto.setName(subjectEntity.getName());

                listSubjectDto.add(subjectDto);
            });

            return listSubjectDto;
        }
    }

    @RequestMapping(value = "/refrashStudyGroup", produces = APPLICATION_JSON_UTF8_VALUE, method = GET)
    public List<StudyGroupDto> findAllStudyGroup() {
        System.out.println("TeacherController - findAllStudyGroup");

        List<StudyGroupDto> listStudyGroupDto = new ArrayList<>();
        List<StudyGroupEntity> listStudyGroupEntity = studyGroupService.findAll();

        if (listStudyGroupEntity == null) {
            throw new NotFoundException("Нет групп");
        }
        else {
            listStudyGroupEntity.forEach(studyGroupEntity -> {
                StudyGroupDto studyGroupDto = new StudyGroupDto();
                studyGroupDto.setId(studyGroupEntity.getId());
                studyGroupDto.setName(studyGroupEntity.getName());

                listStudyGroupDto.add(studyGroupDto);
            });

            return listStudyGroupDto;
        }
    }

    @RequestMapping(value = "/getStudentsByGroup/{id}", produces = APPLICATION_JSON_UTF8_VALUE, method = GET)
    public List<UserDto> findUserByGroupId(@PathVariable("id") int id) {
        System.out.println("TeacherController - findUserByGroupId - " + id);

        List<UserDto>       listUserDto = new ArrayList<>();
        List<UserEntity> listUserEntity = userService.findUserByGroupId(id);

        if (listUserEntity == null) {
            throw new NotFoundException("Нет студентов");
        }
        else {
            listUserEntity.forEach(userEntity -> {
                UserDto userDto = new UserDto();
                userDto.setId(userEntity.getId());
                userDto.setName(userEntity.getName());

                StudyGroupDto studyGroupDto = new StudyGroupDto();
                studyGroupDto.setId(userEntity.getStudyGroupByStudyGroup().getId());
                studyGroupDto.setName(userEntity.getStudyGroupByStudyGroup().getName());

                userDto.setStudyGroupByStudyGroup(studyGroupDto);

                //Обработка промежуточной таблицы
                List<UserExcerciseEntity> listUserExcerciseEntity = userEntity.getUserExcercisesById();
                List<ExcerciseDto> listExcerciseDto = new ArrayList<>();

                if (listUserExcerciseEntity != null) {

                    listUserExcerciseEntity.forEach(userExcerciseEntity -> {

                        ExcerciseDto excerciseDto = new ExcerciseDto();

                        excerciseDto.setId(userExcerciseEntity.getExcerciseByExcercise().getId());
                        excerciseDto.setDate(userExcerciseEntity.getExcerciseByExcercise().getDate());

                        listExcerciseDto.add(excerciseDto);
                    });
                }

                userDto.setExcercisesById(listExcerciseDto);

                listUserDto.add(userDto);
            });

            return listUserDto;
        }
    }

    @RequestMapping(value = "/getExcercises/{idSubject}/{idGroup}",
            produces = APPLICATION_JSON_UTF8_VALUE, method = GET)
    public List<ExcerciseDto> findExcercises(@PathVariable("idSubject") int idSubject,
                                             @PathVariable("idGroup") int idGroup)
    {
        System.out.println("TeacherController - findExcercises - " + idSubject + " " + idGroup);

        List<ExcerciseEntity> listExcerciseEntity = excerciseService.findAll();
        List<ExcerciseDto>       listExcerciseDto = new ArrayList<>();

        listExcerciseEntity.forEach(excerciseEntity -> {
            ExcerciseDto excerciseDto = new ExcerciseDto();
            excerciseDto.setId(excerciseEntity.getId());
            excerciseDto.setDate(excerciseEntity.getDate());

            //Проверка соответствия группы и предмета
            int subject = excerciseEntity.getSubjectBySubjectId().getId();
            int group   = excerciseEntity.getGroupId();

            if ((subject == idSubject) && (group == idGroup)) {
                listExcerciseDto.add(excerciseDto);
            }
        });

        return listExcerciseDto;
    }

    @RequestMapping(value = "/saveExcercise", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, method = RequestMethod.POST)
    public void saveExcercise(ExcerciseDto excerciseDto) {
        System.out.println("TeacherController - saveExcercise");

        ExcerciseEntity excerciseEntity = new ExcerciseEntity();

        SubjectEntity subjectEntity = subjectService.findById(excerciseDto.getSubjectId());

        excerciseEntity.setId(excerciseDto.getId());
        excerciseEntity.setDate(excerciseDto.getDate());
        excerciseEntity.setGroupId(excerciseDto.getGroupId());
        excerciseEntity.setSubjectBySubjectId(subjectEntity);

        excerciseService.save(excerciseEntity);
    }

    @RequestMapping(value = "/saveUserExcercise", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, method = RequestMethod.POST)
    public void saveUserExcercise(UserExcerciseDto userExcerciseDto) {
        System.out.println("TeacherController - saveUserExcercise");

        int userId = userExcerciseDto.getUserId();
        int excerciseId = userExcerciseDto.getExcerciseId();

        UserExcerciseEntity userExcerciseEntity = userExcerciseService.findByUE(userId,excerciseId);

        if (userExcerciseEntity == null) {
            //Создаем нового
            userExcerciseEntity = new UserExcerciseEntity();
            userExcerciseEntity.setId(0);

            UserEntity userEntity = userService.findById(userId);
            ExcerciseEntity excerciseEntity = excerciseService.findById(excerciseId);

            userExcerciseEntity.setUserByUser(userEntity);
            userExcerciseEntity.setExcerciseByExcercise(excerciseEntity);

            userExcerciseService.save(userExcerciseEntity);
        } else {
            //Удаляем запись из таблицы
            userExcerciseService.delete(userExcerciseEntity);
        }
    }
}