package ru.yp.sprint4pw.integration.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yp.sprint4pw.model.Comment;
import ru.yp.sprint4pw.model.Post;
import ru.yp.sprint4pw.repository.JdbcPostRepository;
import ru.yp.sprint4pw.repository.PostRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@Import(JdbcPostRepository.class)
class JdbcPostRepositoryTest {

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

    @Test
    void addPost_shouldAddPostToDatabase() {
        Post post = new Post();
        post.setTitle("New Post");
        post.setText("New Post Text");
        post.setTags(List.of());

        Post result = postRepository.addPost(post);

        assertNotNull(result);
        assertEquals("New Post", result.getTitle());
        assertEquals("New Post Text", result.getText());
    }

    @Test
    void editPost_shouldEditPostInDatabase() {
        Post post = new Post();
        post.setId(1);
        post.setTitle("New Post");
        post.setText("New Post Text");
        post.setTags(List.of());

        Post result = postRepository.editPost(post);

        assertNotNull(result);
        assertEquals(1, post.getId());
        assertEquals("New Post", result.getTitle());
        assertEquals("New Post Text", result.getText());
    }

    @Test
    void deletePost_shouldRemovePostFromDatabase() {
        postRepository.deletePost(1);

        List<Post> posts = postRepository.getPosts("");
        assertEquals(2, posts.size());
        assertTrue(posts.stream().noneMatch(p -> p.getId().equals(1)));
    }

    @Test
    void addLike_shouldAddOneLikeToPost() {
        postRepository.addLike(1);

        Post post = postRepository.getPost(1);
        assertEquals(1, post.getLikesCount());
    }

    @Test
    void getComments_shouldReturnAllCommentsForPost() {
        List<Comment> comments = postRepository.getComments(3);

        assertNotNull(comments);
        assertEquals(2, comments.size());
    }

    @Test
    void getComment_shouldReturnSpecificCommentForPost() {
        Comment comment = postRepository.getComment(3, 2);

        assertNotNull(comment);
        assertEquals(2, comment.getId());
        assertEquals("Comment Text #2", comment.getText());
        assertEquals(3, comment.getPostId());
    }

    @Test
    void addComment_shouldAddCommentToPost() {
        Comment comment = new Comment();
        comment.setText("New Comment Text");
        comment.setPostId(3);

        Comment result1 = postRepository.addComment(3, comment);
        List<Comment> result2 = postRepository.getComments(3);
        assertNotNull(result1);
        assertEquals("New Comment Text", result1.getText());
        assertEquals(3, result1.getPostId());

        assertNotNull(result2);
        assertEquals(3, result2.size());
    }

    @Test
    void editComment_shouldEditCommentForPost() {
        Comment comment = new Comment();
        comment.setId(1);
        comment.setText("New Comment Text");
        comment.setPostId(3);

        Comment result = postRepository.editComment(comment);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("New Comment Text", result.getText());
        assertEquals(3, result.getPostId());
    }

    @Test
    void deletePost_shouldRemoveCommentFromPost() {
        postRepository.deleteComment(1);

        List<Comment> comments = postRepository.getComments(3);
        assertEquals(1, comments.size());
        assertTrue(comments.stream().noneMatch(c -> c.getId().equals(1)));
    }
}