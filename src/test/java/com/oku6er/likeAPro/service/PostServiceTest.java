package com.oku6er.likeAPro.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oku6er.likeAPro.model.Comment;
import com.oku6er.likeAPro.model.Post;
import com.oku6er.likeAPro.model.Tag;
import com.oku6er.likeAPro.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {

    @MockBean
    private PostRepository postRepository;

    private Post postSample;

    @BeforeEach
    void init() {
        Tag programmingTag = new Tag(1L, "programming");
        postSample = new Post(1L,
                "About Java",
                "aboutJava",
                "Post about Java",
                Arrays.asList(programmingTag, new Tag(2L, "java")),
                new ArrayList<>(),
                LocalDateTime.now());
    }


    @Test
    void getAllToDos() {
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
