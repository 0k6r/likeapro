package com.oku6er.likeAPro.service

import com.oku6er.likeAPro.BaseIntegrationTest
import com.oku6er.likeAPro.model.Language
import com.oku6er.likeAPro.model.Tag
import com.oku6er.likeAPro.model.post.Post
import com.oku6er.likeAPro.model.post.Text
import com.oku6er.likeAPro.service.post.IPostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ComponentScan(basePackages = 'com.oku6er.likeAPro.service')
class PostServiceItSpec extends BaseIntegrationTest {

    @Autowired IPostService postService

    def 'If create a post'() {
        given: 'post'
            def post = new Post().setTitle('Java').setSlug('java').setText(new Text()).setVote(10)
                    .setLanguage(Language.EN)
            post.addTag(new Tag('Java'))
        when: 'save new post'
            def savedPost = postService.save(post)
        then: 'post saved and id cannot be null'
            savedPost.id != null
            savedPost.title == 'Java'
    }

    def 'If find all post'() {
        given: '2 saved posts'
            def post = new Post().setTitle('Java').setSlug('java').setText(new Text()).setVote(10)
                    .setLanguage(Language.EN)
            post.addTag(new Tag('Java'))
            def post2 = new Post().setTitle('Net Core').setSlug('net-core').setText(new Text()).setVote(1)
                    .setLanguage(Language.RU)
            post2.addTag(new Tag('Net Core'))
            postService.save(post)
            postService.save(post2)
        when: 'call find all posts'
            List<Post> posts = postService.findAll()
        then: 'have list with 2 posts'
            posts != null
            posts.size() == 2
            posts.get(0).title == 'Java'
    }
}
