package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.repository.PostRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class PostServiceTest {

    @MockBean
    private PostRepository postRepository;
}
