package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.model.Language;
import com.oku6er.likeAPro.model.post.Post;
import com.oku6er.likeAPro.model.post.Text;
import com.oku6er.likeAPro.service.post.IPostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Testcontainers
@DisplayName("Post service integration tests")
class PostServiceTest extends AbstractIntegrationTest {

    @Autowired
    private IPostService postService;

    @Test
    @DisplayName("Throw exception when save new post without required fields")
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

    @DisplayName("When save new post")
    @ParameterizedTest(name = "{index} => title=''{0}'', slug=''{1}'', vote=''{2}'', language=''{3}''")
    @CsvSource({
            "Java, java, 10, EN",
            ".NET, net, 20, RU"
    })
    void saveTag_WhenSaveTag_ThenReturnSavedTag(String title, String slug, Integer vote, Language language) {
        final var post = new Post().setTitle(title).setSlug(slug).setText(new Text()).setVote(vote)
                .setLanguage(language);
        final var savedPost = postService.save(post);

        assertNotNull(savedPost.getId());
        assertEquals(post.getTitle(), savedPost.getTitle());
        assertEquals(post.getSlug(), savedPost.getSlug());
        assertEquals(post.getVote(), savedPost.getVote());
        assertEquals(post.getLanguage(), savedPost.getLanguage());
        assertEquals(post.hashCode(), savedPost.hashCode());
    }

    @Test
    @DisplayName("When given all posts from db")
    @Transactional
    void givenAllPostsInDB_WhenGetAllPostFromDB_ThenGetCountOfPosts() {
        var postSample = new Post().setTitle("About Java").setSlug("about-java").setText(new Text())
                .setVote(0).setLanguage(Language.EN);
        var postSample2 = new Post().setTitle("About .NET").setSlug("about-net").setText(new Text())
                .setVote(0).setLanguage(Language.RU);
        postService.save(postSample);
        postService.save(postSample2);
        assertThat(postService.findAll().size(), is(2));
    }
}
