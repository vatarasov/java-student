package ru.vtarasov.java.student;

/**
 * @author vtarasov
 * @since 30.09.2019
 */
public interface ServerContext {
    String getAddress();
    RequestMapper getMapper();
    ServiceContainer getContainer();
}
