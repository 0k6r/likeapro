package com.oku6er.likeAPro.controller;

import com.oku6er.likeAPro.model.Tag;
import com.oku6er.likeAPro.service.ITagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class TagControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ITagService tagService;

    private List<Tag> tags = new ArrayList<>();

    @BeforeEach
    void init() {
        var tag = new Tag("Java");
        var tag2 = new Tag(".NET");
        tags.add(tag);
        tags.add(tag2);
    }

    @Test
    void givenAllTags_WhenGetAllRequest_ThenGetAllTags() throws Exception {
        when(tagService.findAll()).thenReturn(tags);

        mockMvc.perform(MockMvcRequestBuilders.get("/tags")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }
}
