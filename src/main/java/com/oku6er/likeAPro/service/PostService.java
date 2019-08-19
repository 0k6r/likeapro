package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.model.Post;
import com.oku6er.likeAPro.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post save(final Post post) {
        return postRepository.save(post);
    }
}
