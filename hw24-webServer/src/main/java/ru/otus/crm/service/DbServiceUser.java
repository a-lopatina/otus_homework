package ru.otus.crm.service;

import ru.otus.crm.model.User;

import java.util.Optional;

public interface DbServiceUser {

    void saveUser(User user);

    Optional<User> getUserByLogin(String login);
}
