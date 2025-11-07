package ru.ya.spring3pw.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ya.spring3pw.dto.PostsSearchResponse;
import ru.ya.spring3pw.model.Comment;
import ru.ya.spring3pw.model.Post;
import ru.ya.spring3pw.service.FilesService;
import ru.ya.spring3pw.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService service;
    private final FilesService filesService;

    public PostController(PostService service, FilesService filesService) {
        this.service = service;
        this.filesService = filesService;
    }

    @GetMapping
    public PostsSearchResponse getPosts(@RequestParam("search") String search,
                                        @RequestParam("pageNumber") Integer pageNumber,
                                        @RequestParam("pageSize") Integer pageSize) {
        return service.getPosts(search, pageNumber, pageSize);
    }

    @GetMapping(value = "/{post_id}")
    public Post getPost(@PathVariable("post_id") Integer post_id) {
        return service.getPost(post_id);
    }
}