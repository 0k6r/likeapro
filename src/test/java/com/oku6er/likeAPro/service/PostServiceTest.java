package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.model.Language;
import com.oku6er.likeAPro.model.post.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Java6Assertions.assertThat;

@Testcontainers
public class PostServiceTest extends AbstractIntegrationTest {

    @Autowired
    private IPostService postService;

    private Post postSample;
    private Post postSample2;

    @BeforeEach
    void init() {
        postSample = new Post(1L, "About Java", "aboutJava", null, 0, Language.RU,
                null,
                null,
                LocalDateTime.now());
        postSample2 = new Post(2L,
                "About Payton",
                "aboutPayton",
               null,
                2,
                Language.EN,
                null,
                null,
                LocalDateTime.now());
    }

    @Test
    @DisplayName("Given all posts from db")
    public void givenAllPostsInDB_WhenGetAllPostFromDB_ThenGetCountOfPosts() {
        postService.save(postSample);
        postService.save(postSample2);
        assertThat(postService.findAll().size()).isEqualTo(2);
    }
}
