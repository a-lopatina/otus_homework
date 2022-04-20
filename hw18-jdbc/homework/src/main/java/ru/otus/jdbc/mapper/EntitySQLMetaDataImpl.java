package ru.otus.jdbc.mapper;


import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select * from %s", entityClassMetaData.getName().toLowerCase(Locale.ROOT));
    }

    @Override
    public String getSelectByIdSql() {
        return getSelectAllSql() + String.format(" where %s = ?", entityClassMetaData.getIdField().getName());
    }

    @Override
    public String getInsertSql() {
        var fields = entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
        var values = String.join(", ", Collections.nCopies(entityClassMetaData.getFieldsWithoutId().size(), "?"));
        return String.format("insert into %s (%s) values (%s)", entityClassMetaData.getName().toLowerCase(Locale.ROOT), fields, values);
    }

    @Override
    public String getUpdateSql() {
        var params = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> field.getName() + " = ?")
                .collect(Collectors.joining(", "));
        return String.format("update %s set %s where %s = ?", entityClassMetaData.getName().toLowerCase(Locale.ROOT),
        params, entityClassMetaData.getIdField());
    }
}
