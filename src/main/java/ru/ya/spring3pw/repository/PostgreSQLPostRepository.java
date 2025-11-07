package ru.ya.spring3pw.repository;

import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Conditional;
import ru.ya.spring3pw.model.Comment;
import ru.ya.spring3pw.model.Post;
import ru.ya.spring3pw.repository.condition.PostgreSQLDataBaseCondition;

import java.util.Arrays;
import java.util.List;

@Repository
@Conditional(PostgreSQLDataBaseCondition.class)
public class PostgreSQLPostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;
    private final Environment environment;

    public PostgreSQLPostRepository(JdbcTemplate jdbcTemplate, Environment environment) {
        this.jdbcTemplate = jdbcTemplate;
        this.environment = environment;
    }

    @Override
    public List<Post> getPosts(String search) {
        return List.of();
    }
}