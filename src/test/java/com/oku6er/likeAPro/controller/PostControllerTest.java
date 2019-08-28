package com.oku6er.likeAPro.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.oku6er.likeAPro.model.Comment;
import com.oku6er.likeAPro.model.Language;
import com.oku6er.likeAPro.model.post.Post;
import com.oku6er.likeAPro.model.Tag;
import com.oku6er.likeAPro.model.post.Paragraph;
import com.oku6er.likeAPro.model.post.Text;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private List<Post> postList;
    private Post postSample;
    private LocalDateTime createDate;

    @BeforeEach
    void init() {
        postList = new ArrayList<>();
        Tag programmingTag = new Tag(1L, "programming");
        createDate = LocalDateTime.now();
        postSample = new Post(1L,
                "About Java",
                "aboutJava",
                Collections.singletonList(new Text("",
                        Collections.singletonList(new Paragraph(1, "Post about Java")),
                        "post-about-java-1")),
                0,
                Language.RU,
                new HashSet<>(Arrays.asList(programmingTag, new Tag(2L, "java"))),
                new ArrayList<>(),
                createDate);
        postList.add(postSample);
        postList.add(new Post(2L,
                "About .NET",
                "aboutDotNet",
                Collections.singletonList(new Text("",
                        Collections.singletonList(new Paragraph(1, "Post about .NET")),
                        "post-about-net-1")),
                2,
                Language.EN,
                new HashSet<>(Arrays.asList(programmingTag, new Tag(3L, "net"))),
                Collections.singletonList(new Comment(1L, "Nice post")),
                createDate));
    }

    @Test
    void successfullyCreateAPost() throws Exception {
        when(postService.save(any(Post.class))).thenReturn(postSample);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        String eatPostJSON = objectMapper.writeValueAsString(postSample);

        ResultActions result = mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eatPostJSON));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("About Java"))
                .andExpect(jsonPath("$.slug").value("aboutJava"))
                .andExpect(jsonPath("$.language").value("RU"))
                .andExpect(jsonPath("$.createDate").value(createDate.toString()))
        ;
    }

    @Test
    void getAllPosts() throws Exception {
        when(postService.findAll()).thenReturn(postList);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }
}
