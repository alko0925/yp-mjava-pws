package ru.ya.spring3pw.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.ya.spring3pw.dto.PostsSearchResponse;
import ru.ya.spring3pw.model.Comment;
import ru.ya.spring3pw.model.Post;
import ru.ya.spring3pw.repository.PostRepository;
import ru.ya.spring3pw.service.PostService;
import ru.ya.spring3pw.unit.service.configuration.TestConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = TestConfiguration.class)
@ActiveProfiles("test")
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @BeforeEach
    void resetMocks() {
        reset(postRepository);
    }

    @Test
    void testGetPosts() {
        String search = "aaa";
        doReturn(List.of()).when(postRepository).getPosts(search);
    }
}
