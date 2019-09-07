package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.model.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
public class TagServiceTest extends AbstractIntegrationTest {

    @Autowired
    private ITagService tagService;

    @Test
    @DisplayName("When save new tag without required fields")
    @Transactional
    void saveTag_WhenSaveTagWithoutTitle_ThenThrowException() {
        var ex = assertThrows(ConstraintViolationException.class, () -> {
            var tag = new Tag();
            tagService.save(tag);
        });

        assertAll("Tag required fields",
                () -> assertThat(ex.getMessage(), containsString("Title must not be null")));
    }
}
