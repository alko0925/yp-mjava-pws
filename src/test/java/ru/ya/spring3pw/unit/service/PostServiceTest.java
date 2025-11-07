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
        String search = "Test search criterias";
        Integer pageNumber = 1;
        Integer pageSize = 5;

        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1, "Test Post Name 1", "Test Post Text 1", List.of(), 0, 0));
        posts.add(new Post(2, "Test Post Name 2", "Test Post Text 2", List.of(), 0, 0));
        posts.add(new Post(3, "Test Post Name 3", "Test Post Text 3", List.of(), 0, 0));

        doReturn(posts).when(postRepository).getPosts(search);
        PostsSearchResponse postsSearchResponse  = postService.getPosts(search, pageNumber, pageSize);

        assertEquals(3, postsSearchResponse.getPosts().size(), "Wrong number of posts");
        verify(postRepository, times(1)).getPosts(search);
    }

    @Test
    void testGetPost() {
        Integer post_id = 1;
        Post post = new Post(post_id, "Test Post Name 1", "Test Post Text 1", List.of(), 0, 0);

        doReturn(post).when(postRepository).getPost(post_id);
        Post result  = postService.getPost(post_id);

        assertEquals(post_id, result.getId(), "Wrong post was retrieved");
        verify(postRepository, times(1)).getPost(post_id);
    }

    @Test
    void testAddPost() {
        Integer post_id = 1;
        Post post = new Post();
        post.setId(post_id);
        post.setTitle("Test Post Name 1");
        post.setText("Test Post Text 1");
        post.setTags(List.of());

        doReturn(post).when(postRepository).addPost(post);
        Post result  = postService.addPost(post);

        assertEquals(post_id, result.getId(), "Post was not added properly");
        verify(postRepository, times(1)).addPost(post);
    }

    @Test
    void testEditPost() {
        Integer post_id = 1;
        Post post = new Post();
        post.setId(post_id);
        post.setTitle("Test Post Name 1");
        post.setText("Test Post Text 1");
        post.setTags(List.of());

        doReturn(post).when(postRepository).editPost(post);
        Post result  = postService.editPost(post);

        assertEquals(post_id, result.getId(), "Post was not edited properly");
        verify(postRepository, times(1)).editPost(post);
    }

    @Test
    void testDeletePost() {
        Integer post_id = 1;

        doNothing().when(postRepository).deletePost(post_id);
        postService.deletePost(post_id);

        verify(postRepository, times(1)).deletePost(post_id);
    }

    @Test
    void testAddLike() {
        Integer post_id = 1;
        Integer actualLikesCount = 7;

        when(postRepository.addLike(post_id)).thenReturn(actualLikesCount);
        Integer result = postService.addLike(post_id);

        assertEquals(actualLikesCount, result, "Expected count of likes is wrong");
        verify(postRepository, times(1)).addLike(post_id);
    }

    @Test
    void testGetComments() {
        Integer post_id = 1;

        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1, "Test Comment Text 1", post_id));
        comments.add(new Comment(2, "Test Comment Text 2", post_id));
        comments.add(new Comment(3, "Test Comment Text 3", post_id));

        doReturn(comments).when(postRepository).getComments(post_id);
        List<Comment> result  = postService.getComments(post_id);

        assertEquals(3, result.size(), "Wrong number of comments");
        verify(postRepository, times(1)).getComments(post_id);
    }
}
