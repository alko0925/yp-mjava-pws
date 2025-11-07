package ru.ya.spring3pw.repository.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class PostgreSQLDataBaseCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String dbname = context.getEnvironment().getProperty("database.type");
        return dbname.equalsIgnoreCase("POSTGRESQL");
    }
}