package ru.ya.spring3pw.integration.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.ya.spring3pw.configuration.DataSourceConfiguration;
import ru.ya.spring3pw.model.Comment;
import ru.ya.spring3pw.model.Post;
import ru.ya.spring3pw.repository.H2PostRepository;
import ru.ya.spring3pw.repository.PostRepository;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, H2PostRepository.class})
@TestPropertySource(locations = "classpath:test-application.properties")
class H2PostRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getPosts_shouldReturnAllPosts() {
        List<Post> posts = postRepository.getPosts("");
        assertNotNull(posts);
    }
}
