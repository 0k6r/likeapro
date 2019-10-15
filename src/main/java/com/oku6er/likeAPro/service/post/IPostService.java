package com.oku6er.likeAPro.service.post;

import com.oku6er.likeAPro.model.post.Post;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

public interface IPostService {
    List<Post> findAll();
    Post save(@Valid final Post post);
}
