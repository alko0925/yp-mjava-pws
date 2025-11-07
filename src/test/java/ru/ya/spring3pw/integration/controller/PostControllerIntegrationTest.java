package ru.ya.spring3pw.integration.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.ResponseActions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.ya.spring3pw.configuration.DataSourceConfiguration;
import ru.ya.spring3pw.configuration.WebConfiguration;
import ru.ya.spring3pw.model.Post;
import ru.ya.spring3pw.repository.PostRepository;


import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig(classes = WebConfiguration.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test-application.properties")
class PostControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        //mockMvc = standaloneSetup(userController).build();

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
    void getPosts_returnsJsonArray() throws Exception {
        mockMvc.perform(get("/posts?search=Post&pageNumber=1&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.posts", hasSize(3)))
                .andExpect(jsonPath("$.posts[0].title").value("Post #1"))
                .andExpect(jsonPath("$.posts[1].title").value("Post 2"))
                .andExpect(jsonPath("$.posts[2].title").value("Post #3"));
    }

    @Test
    void getPost_returnsJsonPost() throws Exception {
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(6)))
                .andExpect(jsonPath("$.title").value("Post #1"));
    }

    @Test
    void addPost_acceptsJson_andPersists() throws Exception {
        String json = """
                  {"title":"Post #4","text":"Post Text #4","tags":[]}
                """;

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(6)))
                .andExpect(jsonPath("$.title").value("Post #4"));

        mockMvc.perform(get("/posts?search=Post&pageNumber=1&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(4)));
    }
}
