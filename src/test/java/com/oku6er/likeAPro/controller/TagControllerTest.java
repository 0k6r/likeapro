package com.oku6er.likeAPro.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oku6er.likeAPro.model.Tag;
import com.oku6er.likeAPro.service.tag.ITagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TagController.class)
@Profile("test")
class TagControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ITagService tagService;

    private List<Tag> tags = new ArrayList<>();
    private Tag tag;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void init() {
        tag = new Tag("Java");
        var tag2 = new Tag(".NET");
        tags.add(tag);
        tags.add(tag2);
    }

    @Test
    @DisplayName("When get all tags request")
    void givenAllTags_WhenGetAllRequest_ThenGetAllTags() throws Exception {
        when(tagService.findAll()).thenReturn(tags);

        mockMvc.perform(MockMvcRequestBuilders.get("/tags")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    @DisplayName("When get save tag request")
    void savePost_WhenSaveTag_ThenResponseNoContent() throws Exception {
        var tag = new Tag("Testing");
        when(tagService.save(any(Tag.class))).thenReturn(tag);

        mockMvc.perform(post("/tags")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsString(tag)))
                .andExpect(jsonPath("$.title").value(tag.getTitle()));
    }
}
