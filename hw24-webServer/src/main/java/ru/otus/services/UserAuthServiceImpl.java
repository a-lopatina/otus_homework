package ru.otus.services;

import ru.otus.crm.model.User;
import ru.otus.crm.service.DbServiceUser;

public class UserAuthServiceImpl implements UserAuthService {

    private final DbServiceUser dbServiceUser;

    public UserAuthServiceImpl(DbServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return password.equals(dbServiceUser.getUserByLogin(login).orElse(new User()).getPassword());
    }
}
