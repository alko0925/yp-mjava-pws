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
        List<Post> posts = postRepository.getPosts(search);
        boolean hasPrev = false;
        boolean hasNext = false;
        int lastPage = 0;

        if (posts.size() <= pageSize) lastPage = 1;
        else {
            lastPage = posts.size() / pageSize;
            lastPage = (posts.size() % pageSize == 0) ? lastPage : lastPage + 1;
        }

        if (pageNumber == 1) {
            posts = posts.stream().limit(pageSize).toList();
            hasNext = lastPage > pageNumber;
        } else if (pageNumber <= lastPage) {
            posts = posts.stream().skip((long) (pageNumber - 1) * pageSize).limit(pageSize).toList();
            hasPrev = true;
            hasNext = lastPage > pageNumber;
        } else {
            posts = List.of();
        }

        return new PostsSearchResponse(posts, hasPrev, hasNext, lastPage);
    }

    public Post getPost(Integer id) {
        return postRepository.getPost(id);
    }
}