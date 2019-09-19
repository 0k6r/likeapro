package com.oku6er.likeAPro.service.post;

import com.oku6er.likeAPro.model.post.Post;
import com.oku6er.likeAPro.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class PostService implements IPostService {

    private final PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post save(final Post post) {
        return postRepository.saveAndFlush(post);
    }
}
