package ru.otus.demo;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DbServiceClientImpl;

import java.util.NoSuchElementException;

public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        HwCache<String, Client> hwCache = new MyCache<>();
///
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate, hwCache, false);

        dbServiceClient.saveClient(new Client("dbService1"));
        dbServiceClient.saveClient(new Client("dbService2"));
        dbServiceClient.saveClient(new Client("dbService3"));

        var client = dbServiceClient.saveClient(new Client("dbServiceClient"));

        dbServiceClient.saveClient(new Client("dbService4"));
        dbServiceClient.saveClient(new Client("dbService5"));
        dbServiceClient.saveClient(new Client("dbService6"));
        dbServiceClient.saveClient(new Client("dbService7"));
        dbServiceClient.saveClient(new Client("dbService8"));

        long start = System.currentTimeMillis();
        var receivedClient = dbServiceClient.getClient(client.getId())
                .orElseThrow(() -> new NoSuchElementException("Client not found"));
        long end = System.currentTimeMillis();
        log.info("dbServiceClient:{}", receivedClient);
///
        long duration = end - start;
        System.out.println("Время получения клиента в миллисекундах: " +  duration);
    }
}
