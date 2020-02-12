package ru.ugrasu.journal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.ugrasu.journal.dto.DepartmentDto;
import ru.ugrasu.journal.dto.RoleDto;
import ru.ugrasu.journal.dto.StudyGroupDto;
import ru.ugrasu.journal.dto.UserDto;
import ru.ugrasu.journal.exception.NotFoundException;
import ru.ugrasu.journal.model.entities.DepartmentEntity;
import ru.ugrasu.journal.model.entities.RoleEntity;
import ru.ugrasu.journal.model.entities.StudyGroupEntity;
import ru.ugrasu.journal.model.entities.UserEntity;
import ru.ugrasu.journal.model.services.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private StudyGroupService studyGroupService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ExcerciseService excerciseService;

    @Autowired
    private UserExcerciseService userExcerciseService;

    @RequestMapping(value = "/refrashUser", produces = APPLICATION_JSON_UTF8_VALUE, method = GET)
    public List<UserDto> findAllUser() {
        System.out.println("AdminController - findAllUser");

        List<UserDto> listUserDto = new ArrayList<>();
        List<UserEntity> listUserEntity = userService.findAll();

        if (listUserEntity == null) {
            throw new NotFoundException("Нет пользователей");
        }
        else {
            listUserEntity.forEach(userEntity -> {
                UserDto userDto = new UserDto();
                userDto.setId(userEntity.getId());
                userDto.setName(userEntity.getName());
                userDto.setPassword(userEntity.getPassword());

                //Если роль преподаватель - добавляем кафедру в ДТО
                //Если роль Староста или студент - добавляем группу в ДТО

                RoleDto roleDto = new RoleDto();
                roleDto.setId(userEntity.getRoleByRole().getId());
                roleDto.setName(userEntity.getRoleByRole().getName());

                userDto.setRoleByRole(roleDto);

                String role = roleDto.getName();

                if ((role.equals("Староста")) || (role.equals("Студент"))) {
                    StudyGroupDto studyGroupDto = new StudyGroupDto();
                    studyGroupDto.setId(userEntity.getStudyGroupByStudyGroup().getId());
                    studyGroupDto.setName(userEntity.getStudyGroupByStudyGroup().getName());

                    userDto.setStudyGroupByStudyGroup(studyGroupDto);
                }

                if (role.equals("Учитель")) {
                    DepartmentDto departmentDto = new DepartmentDto();
                    departmentDto.setId(userEntity.getDepartmentByDepartment().getId());
                    departmentDto.setName(userEntity.getDepartmentByDepartment().getName());

                    userDto.setDepartmentByDepartment(departmentDto);
                }

                //TODO
                //Предметы у преподавателей не указали!

                listUserDto.add(userDto);
            });

            return listUserDto;
        }
    }

    @RequestMapping(value = "/refrashRole", produces = APPLICATION_JSON_UTF8_VALUE, method = GET)
    public List<RoleDto> findAllRole() {
        System.out.println("AdminController - findAllRole");

        List<RoleDto> listRoleDto = new ArrayList<>();
        List<RoleEntity> listRoleEntity = roleService.findAll();

        if (listRoleEntity == null) {
            throw new NotFoundException("Нет ролей");
        }
        else {
            listRoleEntity.forEach(roleEntity -> {
                RoleDto roleDto = new RoleDto();
                roleDto.setId(roleEntity.getId());
                roleDto.setName(roleEntity.getName());

                listRoleDto.add(roleDto);
            });

            return listRoleDto;
        }
    }

    @RequestMapping(value = "/refrashDepartment", produces = APPLICATION_JSON_UTF8_VALUE, method = GET)
    public List<DepartmentDto> findAllDepartment() {
        System.out.println("AdminController - findAllDepartment");

        List<DepartmentDto> listDepartmentDto = new ArrayList<>();
        List<DepartmentEntity> listDepartmentEntity = departmentService.findAll();

        if (listDepartmentEntity == null) {
            throw new NotFoundException("Нет кафедр");
        }
        else {
            listDepartmentEntity.forEach(departmentEntity -> {
                DepartmentDto departmentDto = new DepartmentDto();
                departmentDto.setId(departmentEntity.getId());
                departmentDto.setName(departmentEntity.getName());

                listDepartmentDto.add(departmentDto);
            });

            return listDepartmentDto;
        }
    }

    @RequestMapping(value = "/refrashStudyGroup", produces = APPLICATION_JSON_UTF8_VALUE, method = GET)
    public List<StudyGroupDto> findAllStudyGroup() {
        System.out.println("AdminController - findAllStudyGroup");

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

    @RequestMapping(value = "/saveUser", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, method = RequestMethod.POST)
    public void saveUser(UserDto userDto) {

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setName(userDto.getName());
        userEntity.setPassword(userDto.getPassword());

        RoleEntity roleEntity = roleService.findById(userDto.getRoleId());
        userEntity.setRoleByRole(roleEntity);

        //Если есть ссылка на группу (студент или староста)
        if (userDto.getStudyGroupId() != 0) {
            StudyGroupEntity studyGroupEntity = studyGroupService.findById(userDto.getStudyGroupId());
            userEntity.setStudyGroupByStudyGroup(studyGroupEntity);
        }

        //Учитель
        if (userDto.getDepartmentId() != 0) {
            DepartmentEntity departmentEntity = departmentService.findById(userDto.getDepartmentId());
            userEntity.setDepartmentByDepartment(departmentEntity);
        }

        userService.save(userEntity);
    }

    @RequestMapping(value = "/deleteUser/{id}", produces = APPLICATION_JSON_UTF8_VALUE, method = GET)
    public void deleteUser(@PathVariable("id") int id) {

        UserEntity userEntity = userService.findById(id);

        if (userEntity != null) {
            //Удаляем связи
            userEntity.getSubjectsById().forEach(subjectEntity -> {

                subjectEntity.getExcercisesById().forEach(excerciseEntity -> {

                    excerciseEntity.getUserExcercisesById().forEach(userExcerciseEntity -> {
                        userExcerciseService.delete(userExcerciseEntity);
                    });

                    excerciseService.delete(excerciseEntity);
                });

                subjectService.delete(subjectEntity);
            });

            userEntity.getUserExcercisesById().forEach(userExcerciseEntity -> {
                userExcerciseService.delete(userExcerciseEntity);
            });

            userService.delete(userEntity);

        }
    }

}
