package com.oku6er.likeAPro.controller;

import com.oku6er.likeAPro.model.Post;
import com.oku6er.likeAPro.service.PostService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private List<Post> postList;

    @BeforeEach
    void init() {
        postList = new ArrayList<>();
        postList.add(new Post(1L,
                "About Java",
                "Post about Java",
                Arrays.asList("programming", "java"),
                new ArrayList<>(),
                LocalDateTime.now()));
        postList.add(new Post(2L,
                "About .NET",
                "Post about .NET",
                Arrays.asList("programming", "net"),
                Collections.singletonList("Nice post"),
                LocalDateTime.now()));
    }

    @Test
    void getAllPosts() throws Exception {
        when(postService.findAll()).thenReturn(postList);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }
}
