package com.oku6er.likeAPro.service.post;

import com.oku6er.likeAPro.model.post.Post;

import java.util.List;

public interface IPostService {
    List<Post> findAll();
    Post save(final Post post);
}
