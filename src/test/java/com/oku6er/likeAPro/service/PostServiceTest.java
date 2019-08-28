package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.model.Language;
import com.oku6er.likeAPro.model.post.Post;
import com.oku6er.likeAPro.model.Tag;
import com.oku6er.likeAPro.model.post.Paragraph;
import com.oku6er.likeAPro.model.post.Text;
import com.oku6er.likeAPro.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    private Post postSample;

    @BeforeEach
    void init() {
        Set<Tag> tagSet = new HashSet<>(Arrays.asList(
                new Tag("programming"),
                new Tag("java")));
        postSample = new Post(1L,
                "About Java",
                "aboutJava",
                Collections.singletonList(new Text("",
                        Collections.singletonList(new Paragraph(1, "Post about Java")),
                        "post-about-java-1")),
                0,
                Language.RU,
                tagSet
                ,
                new ArrayList<>(),
                LocalDateTime.now());
    }

    @Test
    void getAllPosts() {

        postRepository.save(postSample);
        PostService postService = new PostService(postRepository);

        List<Post> posts = postService.findAll();
        Post lastPost = posts.get(posts.size() - 1);

        assertEquals(postSample.getId(), lastPost.getId());
        assertEquals(postSample.getTitle(), lastPost.getTitle());
        assertEquals(postSample.getTexts(), lastPost.getTexts());
        assertEquals(postSample.getTags(), lastPost.getTags());
        assertEquals(postSample.getComments(), lastPost.getComments());
    }

    @Test
    void saveAToDo() {
        PostService postService = new PostService(postRepository);

        postService.save(postSample);

        assertEquals(1.0, postRepository.count());
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }
}
