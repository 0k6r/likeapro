package com.oku6er.likeAPro.service

import com.oku6er.likeAPro.BaseIntegrationTest
import com.oku6er.likeAPro.model.Language
import com.oku6er.likeAPro.model.post.Post
import com.oku6er.likeAPro.model.post.Text
import com.oku6er.likeAPro.service.post.IPostService
import com.oku6er.likeAPro.service.tag.ITagService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ComponentScan(basePackages = 'com.oku6er.likeAPro.service')
@ActiveProfiles(["integration-test"])
@TestPropertySource('classpath:application-test.yml')
class PostTest extends BaseIntegrationTest {

    @Autowired
    @Qualifier('PostService')
    IPostService postService

    @Autowired ITagService tagService;

    @Autowired
    TestEntityManager entityManager;

    def 'save new post'() {
        given: 'post'
            def post = new Post().setTitle('Java').setSlug('java').setText(new Text()).setVote(10)
                .setLanguage(Language.EN)
        when: 'save post'
            def savedPost = postService.save(post)
        then: 'post saved'
            savedPost.id != null
            savedPost.title == 'Java'
            savedPost.slug == 'java'
        System.err.println(tagService.findAll())
    }
}
