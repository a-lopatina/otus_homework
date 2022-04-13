package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.otus.crm.model.Id;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData{
    private Class<T> clazz;

    public EntityClassMetaDataImpl(Class<T> clazz){
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor getConstructor() throws NoSuchMethodException {
        return clazz.getConstructor();
    }

    @Override
    public Field getIdField() {
        Field[] fields = clazz.getDeclaredFields();
        Field idField = null;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
            }
        }
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.asList(clazz.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        Field[] fields = clazz.getDeclaredFields();
        List<Field> fieldsWithoutId = new ArrayList<>();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Id.class)) {
                fieldsWithoutId.add(field);
            }
        }
        return fieldsWithoutId;
    }
}
