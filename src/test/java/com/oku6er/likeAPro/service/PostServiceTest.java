package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.model.Post;
import com.oku6er.likeAPro.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PostServiceTest {

    @MockBean
    private PostRepository postRepository;

    @Test
    void getAllToDos() {
        Post postSample = new Post(1L, "About Java", "Post about Java",
//                Arrays.asList("programming", "java"), new ArrayList<>(), LocalDateTime.now());
                "programming, java", "", LocalDateTime.now());
        postRepository.save(postSample);
        PostService toDoService = new PostService(postRepository);

        List<Post> posts = toDoService.findAll();
        Post lastPost = posts.get(posts.size() - 1);

        assertEquals(postSample.getId(), lastPost.getId());
        assertEquals(postSample.getTitle(), lastPost.getTitle());
        assertEquals(postSample.getText(), lastPost.getText());
        assertEquals(postSample.getTags(), lastPost.getTags());
        assertEquals(postSample.getComments(), lastPost.getComments());
    }
}
