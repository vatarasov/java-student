package ru.vtarasov.java.student.model;

import ru.vtarasov.java.student.Server;

/**
 * @author vtarasov
 * @since 30.09.2019
 */
public class StudentServer extends Server {
    @Override
    protected void init() {
        getMapper().registerHandler(StudentHandler.PATH, new StudentHandler(this));

        getContainer().register(StudentRegistrationService.class, new StudentRegistrationServiceImpl(new StudentRepositoryImpl()));
    }
}
