package com.ls.qdaliy.bean;

/**
 * Created by asus on 2018/7/21.
 * Describe:
 */
public class SearchResult {
    private Post post;
    private Author author;

    public Post getPost() {
        return post;
    }

    public Author getAuthor() {
        return author;
    }

    public void setPost(Post post) {
        post = post;
    }

    public void setAuthor(Author auther) {
        this.author = auther;
    }
}
