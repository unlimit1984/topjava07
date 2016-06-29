package ru.javawebinar.topjava.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by vladimir on 29.06.2016.
 */
@Converter(autoApply = true)
public class LocalDateTimePersistenceConverter implements
        AttributeConverter<LocalDateTime, Timestamp> {
    @Override
    public java.sql.Timestamp convertToDatabaseColumn(
            LocalDateTime entityValue) {
        return Timestamp.valueOf(entityValue);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(
            java.sql.Timestamp databaseValue) {
        return databaseValue.toLocalDateTime();
    }
}