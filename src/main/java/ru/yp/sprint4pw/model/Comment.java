package ru.yp.sprint4pw.model;

import java.util.List;

public class Comment {
    private Integer id;
    private String text;
    private Integer postId;

    public Comment() {}

    public Comment(Integer id, String text, Integer postId) {
        this.id = id;
        this.text = text;
        this.postId = postId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }
}
