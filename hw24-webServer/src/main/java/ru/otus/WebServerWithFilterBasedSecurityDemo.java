package ru.otus;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.model.User;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.crm.service.DbServiceUser;
import ru.otus.crm.service.DbServiceUserImpl;
import ru.otus.server.WebServer;
import ru.otus.server.WebServerWithFilterBasedSecurity;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;
import ru.otus.services.UserAuthService;
import ru.otus.services.UserAuthServiceImpl;

import java.util.List;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница клиентов
    http://localhost:8080/clients

*/
public class WebServerWithFilterBasedSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        configuration.setProperty("hibernate.connection.url", dbUrl);
        configuration.setProperty("hibernate.connection.username", dbUserName);
        configuration.setProperty("hibernate.connection.password", dbPassword);

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class, User.class);

        TransactionManagerHibernate transactionManager = new TransactionManagerHibernate(sessionFactory);
        DataTemplateHibernate<Client> clientTemplate = new DataTemplateHibernate<>(Client.class);
        DBServiceClient dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        DataTemplateHibernate<User> userTemplate = new DataTemplateHibernate<>(User.class);
        DbServiceUser dbServiceUser = new DbServiceUserImpl(transactionManager, userTemplate);

        var user = new User(null, "Джанет Вэрни", "user6", "11111");
        dbServiceUser.saveUser(user);

        dbServiceClient.saveClient(new Client(null, "dbService1", new Address(null, "Москва"), List.of(new Phone(null, "12324"))));
        dbServiceClient.saveClient(new Client(null, "dbService2", new Address(null, "Самара"), List.of(new Phone(null, "64535"))));
        dbServiceClient.saveClient(new Client("dbService3"));
        dbServiceClient.saveClient(new Client("dbService4"));

        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(dbServiceUser);

        WebServer usersWebServer = new WebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, dbServiceClient, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
