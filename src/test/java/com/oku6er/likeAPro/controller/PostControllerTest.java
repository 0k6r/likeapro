package com.oku6er.likeAPro.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oku6er.likeAPro.model.Language;
import com.oku6er.likeAPro.model.post.Paragraph;
import com.oku6er.likeAPro.model.post.Post;
import com.oku6er.likeAPro.model.post.Text;
import com.oku6er.likeAPro.service.post.IPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostController.class)
@ActiveProfiles("test")
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private IPostService postService;

    private ObjectMapper mapper = new ObjectMapper();

    private List<Post> postList;
    private Post postSample;

    @BeforeEach
    void init() {
        postList = new ArrayList<>();
        postSample = new Post(1L,   "About Java", "aboutJava", new Text("",
                Collections.singletonList(new Paragraph(1, "Post about Java")),
                "post-about-java-1"), 0, Language.RU, null, null);
        postList.add(postSample);
        postList.add(new Post(2L, "About .NET", "aboutDotNet", new Text("",
                Collections.singletonList(new Paragraph(1, "Post about .NET")),
                "post-about-net-1"), 2, Language.EN,
                null, null));
    }

    @Test
    void successfullyCreateAPost() throws Exception {
        when(postService.save(any(Post.class))).thenReturn(postSample);

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(postSample)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("About Java"))
                .andExpect(jsonPath("$.slug").value("aboutJava"))
                .andExpect(jsonPath("$.language").value("RU"));
    }

    @Test
    void getAllPosts() throws Exception {
        when(postService.findAll()).thenReturn(postList);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }
}
