package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.model.User;

import java.util.Optional;

public class DbServiceUserImpl implements DbServiceUser {

    private static final Logger log = LoggerFactory.getLogger(DbServiceUserImpl.class);

    private final DataTemplate<User> userDataTemplate;
    private final TransactionManager transactionManager;

    public DbServiceUserImpl(TransactionManager transactionManager, DataTemplate<User> userDataTemplate) {
        this.transactionManager = transactionManager;
        this.userDataTemplate = userDataTemplate;
    }

    @Override
    public void saveUser(User user) {
        transactionManager.doInTransaction(session -> {
            if (user.getId() == null) {
                userDataTemplate.insert(session, user);
                log.info("created user: {}", user);
            }
            userDataTemplate.update(session, user);
            log.info("updated user: {}", user);
            return user;
        });
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var userList = userDataTemplate.findByEntityField(session, "login", login);
            return userList.stream().findAny();
        });
    }
}
