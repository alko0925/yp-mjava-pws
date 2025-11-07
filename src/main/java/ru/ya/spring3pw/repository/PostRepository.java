package ru.ya.spring3pw.repository;

import ru.ya.spring3pw.model.Comment;
import ru.ya.spring3pw.model.Post;
import java.util.List;

public interface PostRepository {
    List<Post> getPosts(String search);
    Post getPost(Integer id);
    Post addPost(Post user);
    Post editPost(Post post);
    void deletePost(Integer id);
    Integer addLike(Integer id);
    List<Comment> getComments(Integer id);
    Comment getComment(Integer post_id, Integer comment_id);
    Comment addComment(Integer post_id, Comment comment);
    Comment editComment(Comment comment);
    void deleteComment(Integer comment_id);
}
