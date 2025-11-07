package ru.ya.spring3pw.repository;

import org.springframework.context.annotation.Conditional;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.ya.spring3pw.model.Comment;
import ru.ya.spring3pw.model.Post;
import ru.ya.spring3pw.repository.condition.H2DataBaseCondition;

import java.util.Arrays;
import java.util.List;

@Repository
@Conditional(H2DataBaseCondition.class)
public class H2PostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;
    private final Environment environment;

    public H2PostRepository(JdbcTemplate jdbcTemplate, Environment environment) {
        this.jdbcTemplate = jdbcTemplate;
        this.environment = environment;
    }

    @Override
    public List<Post> getPosts(String search) {
        return List.of();
    }
}