package ru.ya.spring3pw.service;

import org.springframework.stereotype.Service;
import ru.ya.spring3pw.dto.PostsSearchResponse;
import ru.ya.spring3pw.model.Comment;
import ru.ya.spring3pw.model.Post;
import ru.ya.spring3pw.repository.PostRepository;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostsSearchResponse getPosts(String search, Integer pageNumber, Integer pageSize) {
        return new PostsSearchResponse();
    }
}