package com.oku6er.likeAPro.controller;

import com.oku6er.likeAPro.model.Tag;
import com.oku6er.likeAPro.service.ITagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.ConstraintViolationException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class TagControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ITagService tagService;

    private Tag tag;
    private Set<Tag> tags;

    @BeforeEach
    void init() {
        tag = new Tag("Java");
        var tag2 = new Tag(".NET");
        tags.add(tag);
        tags.add(tag2);
    }

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

    @Test
    void givenAllTags_WhenGetAllRequest_ThenGetAllTags() throws Exception {
        when(tagService.findAll()).thenReturn(tags);

        mockMvc.perform(MockMvcRequestBuilders.get("/tags")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }
}
