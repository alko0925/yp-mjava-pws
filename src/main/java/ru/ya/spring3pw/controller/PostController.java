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

    @PostMapping(consumes = {"application/json"})
    public Post addPost(@RequestBody Post post) {
        return service.addPost(post);
    }

    @PutMapping(value = "/{post_id}", consumes = {"application/json"})
    public Post editPost(@RequestBody Post post) {
        return service.editPost(post);
    }

    @PutMapping(value = "/{post_id}/image")
    public String uploadImage(@PathVariable("post_id") Integer post_id,
                              @RequestParam("image") MultipartFile image) {
        return filesService.upload(post_id, image);
    }

    @GetMapping("/{post_id}/image")
    public ResponseEntity<Resource> downloadImage(@PathVariable(name = "post_id") Integer post_id) {
        Resource file = filesService.download(post_id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    @DeleteMapping(value = "/{post_id}")
    public void deletePost(@PathVariable(name = "post_id") Integer post_id) {
        service.deletePost(post_id);
    }

    @PostMapping(value = "/{post_id}/likes")
    public Integer addLike(@PathVariable(name = "post_id") Integer post_id) {
        return service.addLike(post_id);
    }

    @GetMapping(value = "/{post_id}/comments")
    public List<Comment> getComments(@PathVariable("post_id") Integer post_id) {
        return service.getComments(post_id);
    }

    @GetMapping(value = "/{post_id}/comments/{comment_id}")
    public Comment getComment(@PathVariable("post_id") Integer post_id,
                              @PathVariable("comment_id") Integer comment_id) {
        return service.getComment(post_id, comment_id);
    }
}