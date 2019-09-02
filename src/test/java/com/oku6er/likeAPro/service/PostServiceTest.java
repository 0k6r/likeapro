package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.model.Language;
import com.oku6er.likeAPro.model.Tag;
import com.oku6er.likeAPro.model.post.Paragraph;
import com.oku6er.likeAPro.model.post.Post;
import com.oku6er.likeAPro.model.post.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@ExtendWith(SpringExtension.class)
public class PostServiceTest extends AbstractIntegrationTest {

    @Test
    @DisplayName("Postgres container is running")
    void test() {
        System.out.printf("postgres db running, db-name: '%s', user: '%s', jdbc-url: '%s'%n ",
                AbstractIntegrationTest.postgreSQLContainer.getDatabaseName(),
                AbstractIntegrationTest.postgreSQLContainer.getUsername(),
                AbstractIntegrationTest.postgreSQLContainer.getJdbcUrl());
        assertTrue(AbstractIntegrationTest.postgreSQLContainer.isRunning());
    }

    @Autowired
    private IPostService postService;

    private Post postSample;
    private Post postSample2;

    @BeforeEach
    void init() {
        Set<Tag> tagSet = new HashSet<>(Arrays.asList(
                new Tag("programming"),
                new Tag("java")));
        postSample = new Post(1L,
                "About Java",
                "aboutJava",
                Collections.singletonList(new Text("",
                        Collections.singletonList(new Paragraph(1, "Post about Java")),
                        "post-about-java-1")),
                0,
                Language.RU,
                tagSet,
                new ArrayList<>(),
                LocalDateTime.now());
        postSample2 = new Post(2L,
                "About Payton",
                "aboutPayton",
                Collections.singletonList(new Text("",
                        Collections.singletonList(new Paragraph(1, "Post about Payton")),
                        "post-about-payton-1")),
                2,
                Language.EN,
                null,
                new ArrayList<>(),
                LocalDateTime.now());
    }

    @Test
    @DisplayName("Given all posts from db")
    @Transactional
    public void givenAllPostsInDB_WhenGetAllPostFromDB_ThenGetCountOfPosts() {
        postService.save(postSample);
        postService.save(postSample2);
        assertThat(postService.findAll().size()).isEqualTo(2);
    }
}
