package ru.ya.spring3pw.dto;

import ru.ya.spring3pw.model.Post;

import java.util.List;

public class PostsSearchResponse {
    private List<Post> posts;
    private Boolean hasPrev;
    private Boolean hasNext;
    private Integer lastPage;

    public PostsSearchResponse() {}

    public PostsSearchResponse(List<Post> posts, Boolean hasPrev, Boolean hasNext, Integer lastPage) {
        this.posts = posts;
        this.hasPrev = hasPrev;
        this.hasNext = hasNext;
        this.lastPage = lastPage;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Boolean getHasPrev() {
        return hasPrev;
    }

    public void setHasPrev(Boolean hasPrev) {
        this.hasPrev = hasPrev;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }
}
