package ru.ya.spring3pw.repository;

import ru.ya.spring3pw.model.Comment;
import ru.ya.spring3pw.model.Post;
import java.util.List;

public interface PostRepository {
    List<Post> getPosts(String search);
    Post getPost(Integer id);
    Post addPost(Post user);
    Post editPost(Post post);
}
