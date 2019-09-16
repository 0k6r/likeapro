package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.model.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Testcontainers
@DisplayName("Tag service integration tests")
public class TagServiceTest extends AbstractIntegrationTest {

    @Autowired
    private ITagService tagService;

    @Test
    @DisplayName("When save new tag without required fields")
    void saveTag_WhenSaveTagWithoutTitle_ThenThrowException() {
        var ex = assertThrows(ConstraintViolationException.class, () -> {
            var tag = new Tag();
            tagService.save(tag);
        });

        assertAll("Tag required fields",
                () -> assertThat(ex.getMessage(), containsString("Title must not be null")));
    }

    @DisplayName("When save new tag")
    @ParameterizedTest(name = "{index} => tag title=''{0}''")
    @ValueSource(strings = {"Java", ".NET"})
    void saveTag_WhenSaveTag_ThenReturnSavedTag(String title) {
        var savedTag = tagService.save(new Tag(title));
        assertEquals(title, savedTag.getTitle());
    }

    @DisplayName("When get tag by id")
    @ParameterizedTest(name = "{index} => tag title=''{0}''")
    @ValueSource(strings = {"Java", ".NET"})
    void getTagById_WhenGetTagById_ThenReturnTagWithCurrentId(String title) {
        var savedTag = tagService.save(new Tag(title));
        var finedTag = tagService.getById(savedTag.getId());
        assertEquals(savedTag, finedTag);
    }
}
