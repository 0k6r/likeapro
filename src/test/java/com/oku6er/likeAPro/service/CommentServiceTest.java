package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.model.Comment;
import com.oku6er.likeAPro.model.Language;
import com.oku6er.likeAPro.model.post.Post;
import com.oku6er.likeAPro.model.post.Text;
import com.oku6er.likeAPro.service.comment.ICommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Testcontainers
@DisplayName("Comment service integration tests")
@ComponentScan("com.oku6er.likeapro.service.comment")
public class CommentServiceTest extends AbstractIntegrationTest {

    private final ICommentService commentService;
    private final TestEntityManager entityManager;

    @Autowired
    public CommentServiceTest(ICommentService commentService, TestEntityManager entityManager) {
        this.commentService = commentService;
        this.entityManager = entityManager;
    }

    @Test
    @DisplayName("Throw exception when save new comment without required fields")
    void saveComment_WhenSaveCommentWithoutRequiredFields_ThenThrowException() {
        final var ex = assertThrows(ConstraintViolationException.class, () -> commentService.save(new Comment()));

        assertAll("Comment required fields",
                () -> assertThat(ex.getMessage(), containsString("Author must not be null")),
                () -> assertThat(ex.getMessage(), containsString("Rating must not be null")),
                () -> assertThat(ex.getMessage(), containsString("Text must not be null")),
                () -> assertThat(ex.getMessage(), containsString("Post must not be null")));
    }

    @DisplayName("When save new comment")
    @ParameterizedTest(name = "{index} => author=''{0}'', rating=''{1}'', text=''{2}''")
    @CsvSource({
            "Jon, 10, Nice post",
            "Ben, 0, Nice comment"
    })
    void saveTag_WhenSaveTag_ThenReturnSavedTag(String author, Integer rating, String text) {
        final var postSample = new Post().setTitle("test").setSlug("test").setText(new Text()).setVote(0)
                .setLanguage(Language.EN);
        final var savedPost = entityManager.persist(postSample);

        final var comment = new Comment().setAuthor(author).setRating(rating).setText(text).setPost(savedPost);
        final var savedComment = commentService.save(comment);

        assertNotNull(savedComment.getId());
        assertEquals(comment.getAuthor(), savedComment.getAuthor());
        assertEquals(comment.getRating(), savedComment.getRating());
        assertEquals(comment.getText(), savedComment.getText());
        assertEquals(comment.hashCode(), savedComment.hashCode());
    }
}
