package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.exception.NotFoundException;
import com.oku6er.likeAPro.model.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Testcontainers
@DisplayName("Tag service integration tests")
class TagServiceTest extends AbstractIntegrationTest {

    @Autowired
    private ITagService tagService;

    @Test
    @DisplayName("Throw exception when save new tag without required fields")
    void saveTag_WhenSaveTagWithoutTitle_ThenThrowException() {
        final var ex = assertThrows(ConstraintViolationException.class, () -> tagService.save(new Tag()));
        assertThat(ex.getMessage(), containsString("Title must not be null"));
    }

    @Test
    @DisplayName("Throw exception when save new tag with nullable title")
    void saveTag_WhenSaveTagWithNullableTitle_ThenThrowException() {
        final var ex = assertThrows(ConstraintViolationException.class, () -> tagService.save(new Tag(null)));
        assertThat(ex.getMessage(), containsString("Title must not be null"));
    }

    @Test
    @DisplayName("Throw exception when save new tag with same title")
    void saveTag_WhenSaveTagWithSameTitle_ThenThrowException() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            final var tag = new Tag("Java");
            tagService.save(tag);
            tagService.save(tag);
        });
    }

    @DisplayName("When save new tag")
    @ParameterizedTest(name = "{index} => tag title=''{0}''")
    @ValueSource(strings = {"Java", ".NET"})
    void saveTag_WhenSaveTag_ThenReturnSavedTag(String title) {
        final var tag = new Tag(title);
        final var savedTag = tagService.save(tag);

        assertNotNull(savedTag.getId());
        assertEquals(tag.getTitle(), savedTag.getTitle());
        assertEquals(tag.hashCode(), savedTag.hashCode());
    }

    @Test
    @DisplayName("Throw exception when save new tag without required fields")
    void getTagById_WhenTagWithIdNotFound_ThenThrowNotFoundException() {
        final var fakeId = 10L;
        var ex = assertThrows(NotFoundException.class, () -> tagService.getById(fakeId));

        assertThat(ex.getMessage(), containsString("Tag with id: '" + fakeId + "' not found"));
    }

    @DisplayName("When get tag by id")
    @ParameterizedTest(name = "{index} => tag title=''{0}''")
    @ValueSource(strings = {"Java", ".NET"})
    void getTagById_WhenGetTagById_ThenReturnTagWithCurrentId(String title) {
        final var savedTag = tagService.save(new Tag(title));
        final var finedTag = tagService.getById(savedTag.getId());

        assertEquals(savedTag, finedTag);
    }
}
