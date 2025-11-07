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
        jdbcTemplate.execute("DELETE FROM posts");

        jdbcTemplate.update(
                "INSERT INTO posts(id, title, text, tags) VALUES(?, ?, ?, ?)",
                1, "Post #1", "Post Text #1", List.of().toString()
        );
        jdbcTemplate.update(
                "INSERT INTO posts(id, title, text, tags) VALUES(?, ?, ?, ?)",
                2, "Post 2", "Post Text #2", List.of().toString()
        );
        jdbcTemplate.update(
                "INSERT INTO posts(id, title, text, tags) VALUES(?, ?, ?, ?)",
                3, "Post #3", "Post Text #3", List.of().toString()
        );

        jdbcTemplate.update(
                "INSERT INTO comments(id, text, post_id) VALUES(?, ?, ?)",
                1, "Comment Text #1", 3
        );
        jdbcTemplate.update(
                "INSERT INTO comments(id, text, post_id) VALUES(?, ?, ?)",
                2, "Comment Text #2", 3
        );
    }

    @Test
    void getPosts_shouldReturnAllPosts() {
        List<Post> posts = postRepository.getPosts("");

        assertNotNull(posts);
        assertEquals(3, posts.size());
    }

    @Test
    void getPosts_shouldReturnJustOnePost() {
        List<Post> posts = postRepository.getPosts("Post 2");

        assertNotNull(posts);
        assertEquals(1, posts.size());
        Post first = posts.getFirst();
        assertEquals(2, first.getId());
        assertEquals("Post Text #2", first.getText());
    }

    @Test
    void getPost_shouldReturnPostById() {
        Post post = postRepository.getPost(1);

        assertNotNull(post);
        assertEquals(1, post.getId());
        assertEquals("Post #1", post.getTitle());
        assertEquals("Post Text #1", post.getText());
    }
}
