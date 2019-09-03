package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.model.post.Post;
import com.oku6er.likeAPro.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService implements IPostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional
    public Post save(final Post post) {
        return postRepository.save(post);
    }
}
