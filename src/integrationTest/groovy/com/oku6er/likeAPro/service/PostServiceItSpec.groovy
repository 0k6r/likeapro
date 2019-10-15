package com.oku6er.likeAPro.service

import com.oku6er.likeAPro.CustomPostgreSQLContainer
import com.oku6er.likeAPro.model.Language
import com.oku6er.likeAPro.model.Tag
import com.oku6er.likeAPro.model.post.Post
import com.oku6er.likeAPro.model.post.Text
import com.oku6er.likeAPro.service.post.IPostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

import javax.validation.ConstraintViolationException

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ComponentScan(basePackages = 'com.oku6er.likeAPro.service')
@Testcontainers
@ActiveProfiles('integration-test')
class PostServiceItSpec extends Specification {

    @Shared PostgreSQLContainer postgreSQLContainer = CustomPostgreSQLContainer.getInstance()
    @Autowired IPostService postService

    @Shared Post post = new Post().setTitle('Java').setSlug('java').setText(new Text())
    @Shared Post post2 = new Post().setTitle('Net Core').setSlug('net-core').setText(new Text())

    def setup() {
        post.addTag(new Tag('Java'))
        post2.addTag(new Tag('Net Core'))
    }

    def 'If create a post'() {
        when: 'save new post'
        def savedPost = postService.save(post)

        then: 'post saved and id cannot be null'
        notThrown(ConstraintViolationException)
        savedPost.id != null
        savedPost.title == 'Java'
    }

    def 'If find all posts'() {
        when: 'call find all posts'
        postService.save(post)
        postService.save(post2)
        List<Post> posts = postService.findAll()

        then: 'have list with 2 posts'
        posts != null
        posts.size() == 2
    }


    def 'If create a post with same slugs throw DataIntegrityViolationException'() {
        given: '2 post with same slugs'
        def postWithSameSlug = new Post().setTitle('New Java').setSlug('java').setText(new Text()).setVote(10)
                .setLanguage(Language.RU)
        postWithSameSlug.addTag(new Tag('Java'))

        when: 'save new posts'
        postService.save(post)
        postService.save(postWithSameSlug)

        then: 'throw exception'
        def ex = thrown(DataIntegrityViolationException)
        ex.message.contains('could not execute statement')
        ex.message.contains('uk_post_slug')

    }
}
