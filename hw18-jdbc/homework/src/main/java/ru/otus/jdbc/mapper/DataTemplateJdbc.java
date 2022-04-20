package ru.otus.jdbc.mapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.core.sessionmanager.DataBaseOperationException;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return handle(rs);
                }
                return null;
            } catch (SQLException e) {
                throw new DataBaseOperationException("exception", e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return (List<T>) dbExecutor.executeSelectAll(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            try {
                List<T> entities = new ArrayList<>();
                while (rs.next()) {
                    entities.add(handle(rs));
                }
                return entities;
            } catch (SQLException e) {
                throw new DataBaseOperationException("exception", e);
            }
        });
    }


    @Override
    public long insert(Connection connection, T client) {
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                entityClassMetaData.getFieldsWithoutId().stream()
                        .map(field -> {
                            try {
                                field.setAccessible(true);
                                return field.get(client);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                                return null;
                            }
                        }).collect(Collectors.toList()));
    }

    @Override
    public void update(Connection connection, T client) {
        List<Object> fieldValues = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> {
                    try {
                        return field.get(client);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return null;
                    }
                }).toList();
        try {
            fieldValues.add(entityClassMetaData.getIdField().get(client));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), fieldValues);
    }

    private T handle(ResultSet rs) {
        try {
            T instance = entityClassMetaData.getConstructor().newInstance();
            entityClassMetaData.getAllFields().forEach(field -> {
                field.setAccessible(true);
                try {
                    field.set(instance, rs.getObject(field.getName(), field.getType()));
                } catch (SQLException | IllegalAccessException ex) {
                    throw new DataBaseOperationException("exception", ex);
                }
            });
            return instance;
        } catch (ReflectiveOperationException e) {
            throw new DataBaseOperationException("exception", e);
        }
    }
}

