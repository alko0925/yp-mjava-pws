package ru.yp.sprint4pw.integration.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
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

    @Test
    void editPost_acceptsJson_andPersists() throws Exception {
        String json = """
                  {"id":"1", "title":"New Title","text":"Post Text #1","tags":[]}
                """;

        mockMvc.perform(put("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(6)))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("New Title"));

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(6)))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("New Title"));
    }


    @Test
    void deletePost_removeFromDatbase() throws Exception {
        mockMvc.perform(delete("/posts/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/posts?search=Post&pageNumber=1&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(2)));
    }

    @Test
    void addLike_AndDownloadAvatar_success() throws Exception {

        mockMvc.perform(post("/posts/1/likes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    void getComments_returnsJsonArray() throws Exception {

        mockMvc.perform(get("/posts/3/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].text").value("Comment Text #2"));
    }

    @Test
    void getComment_returnsJsonComment() throws Exception {

        mockMvc.perform(get("/posts/3/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("Comment Text #1"))
                .andExpect(jsonPath("$.postId").value(3));
    }

    @Test
    void addComment_acceptsJson_andPersists() throws Exception {

        String json = """
                  {"text":"New Comment Text","postId":"3"}
                """;

        mockMvc.perform(post("/posts/3/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.text").value("New Comment Text"))
                .andExpect(jsonPath("$.postId").value(3));
    }

    @Test
    void editComment_acceptsJson_andPersists() throws Exception {

        String json = """
                  {"id":"1","text":"New Comment Text","postId":"3"}
                """;

        mockMvc.perform(put("/posts/3/comments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("New Comment Text"))
                .andExpect(jsonPath("$.postId").value(3));
    }

    @Test
    void deleteComment_removeFromDatbase() throws Exception {
        mockMvc.perform(delete("/posts/3/comments/2"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/posts/3/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}