package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.model.Language;
import com.oku6er.likeAPro.model.post.Post;
import com.oku6er.likeAPro.model.post.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
public class PostServiceTest extends AbstractIntegrationTest {

    @Autowired
    private IPostService postService;

    private Post postSample;
    private Post postSample2;

    @BeforeEach
    void init() {
        postSample = new Post(1L, "About Java", "aboutJava", new Text(), 0, Language.RU,
                null, null);
        postSample2 = new Post(2L, "About Payton", "aboutPayton", new Text(), 2, Language.EN,
                null, null);
    }

    @Test
    @DisplayName("When save new post without required fields")
    @Transactional
    void savePost_WhenSavePostWithoutRequiredFields_ThenThrowException() {
        var ex = assertThrows(ConstraintViolationException.class, () -> {
            var post = new Post();
            postService.save(post);
        });

        assertAll("Post required fields",
                () -> assertThat(ex.getMessage(), containsString("Title must not be null")),
                () -> assertThat(ex.getMessage(), containsString("Slug must not be null")),
                () -> assertThat(ex.getMessage(), containsString("Text must not be null")),
                () -> assertThat(ex.getMessage(), containsString("Vote must not be null")),
                () -> assertThat(ex.getMessage(), containsString("Language must not be null")));
    }

    @Test
    @DisplayName("Given all posts from db")
    @Transactional
    void givenAllPostsInDB_WhenGetAllPostFromDB_ThenGetCountOfPosts() {
        postService.save(postSample);
        postService.save(postSample2);
        assertThat(postService.findAll().size(), is(2));
    }
}
