package ru.vtarasov.java.student.model;

import java.util.Optional;

/**
 * @author vtarasov
 * @since 21.09.2019
 */
public class StudentRegistrationServiceImpl implements StudentRegistrationService {
    private final StudentRepository repository;

    public StudentRegistrationServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student register(Student student) {
        return repository.save(student);
    }

    @Override
    public void unregister(Student student) {
        repository.delete(student.getId());
    }

    @Override
    public Optional<Student> find(String id) {
        return repository.get(id);
    }
}
