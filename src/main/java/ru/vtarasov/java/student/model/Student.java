package ru.vtarasov.java.student.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author vtarasov
 * @since 21.09.2019
 */
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true, builderMethodName = "")
@Getter
public class Student {
    private Student() {
        name = null;
        age = null;
    }

    private String id;
    private final String name;
    private final Integer age;
}
