package com.oku6er.likeAPro.model

import com.oku6er.likeAPro.CustomPostgreSQLContainer
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
class PostITSpec extends Specification {

    @Shared PostgreSQLContainer postgreSQLContainer = CustomPostgreSQLContainer.getInstance()
    @Autowired IPostService postService

    Post post = new Post()

    def setup() {
        post.addTag(new Tag('Java'))
    }

    def 'If create a post without title throw ConstraintViolationException'() {
        given: 'post without title'
        post.setSlug('java').setText(new Text()).setVote(10).setLanguage(Language.EN)

        when: 'save new post'
        postService.save(post)

        then: 'throw exception'
        def ex = thrown(ConstraintViolationException)
        ex.message.contains('The title must not be null')
    }

    def 'If create a post without slug throw ConstraintViolationException'() {
        given: 'post without slug'
        post.setTitle('Java').setText(new Text()).setVote(10).setLanguage(Language.EN)

        when: 'save new post'
        postService.save(post)

        then: 'throw exception'
        def ex = thrown(ConstraintViolationException)
        ex.message.contains('The slug must not be null')
    }

    def 'If create a post where slug has a length more 50 characters throw ConstraintViolationException'() {
        given: 'post where slug has a length more 50 characters'
        def longSlug = 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.'
        post.setTitle('Java').setSlug(longSlug).setText(new Text()).setVote(10)
                .setLanguage(Language.EN)

        when: 'save new post'
        postService.save(post)

        then: 'throw exception'
        def ex = thrown(ConstraintViolationException)
        ex.message.contains('The slug has a max length with 50 characters')
    }

    def 'If create a post with same slugs throw DataIntegrityViolationException'() {
        given: '2 post with same slugs'
        post.setTitle('Java').setSlug('java').setText(new Text()).setVote(10).setLanguage(Language.EN)
        def post2 = new Post().setTitle('New Java').setSlug('java').setText(new Text()).setVote(10)
                .setLanguage(Language.RU)
        post2.addTag(new Tag('Java'))

        when: 'save new posts'
        postService.save(post)
        postService.save(post2)

        then: 'throw exception'
        def ex = thrown(DataIntegrityViolationException)
        ex.message.contains('could not execute statement')
        ex.message.contains('uk_post_slug')
    }

    def 'If create a post without text throw ConstraintViolationException'() {
        given: 'post without slug'
        post.setTitle('Java').setSlug('java').setVote(10).setLanguage(Language.EN)

        when: 'save new post'
        postService.save(post)

        then: 'throw exception'
        def ex = thrown(ConstraintViolationException)
        ex.message.contains('The text must not be null')
    }

    def 'If create a post without vote'() {
        given: 'post without vote'
        post.setTitle('Java').setSlug('java').setText(new Text()).setLanguage(Language.EN)

        when: 'save new post'
        def savedPost = postService.save(post)

        then: 'vote have default value'
        savedPost.vote == 0
    }

    def 'If create a post without language'() {
        given: 'post without language'
        post.setTitle('Java').setSlug('java').setText(new Text()).setVote(10)

        when: 'save new post'
        def savedPost = postService.save(post)

        then: 'language have default value'
        savedPost.language == Language.RU
    }

    def 'If create a post without tags'() {
        given: 'post without tags'
        def post = new Post().setTitle('Java').setSlug('java').setText(new Text()).setVote(10).setLanguage(Language.EN)

        when: 'save new post'
        postService.save(post)

        then: 'language have default value'
        def ex = thrown(ConstraintViolationException)
        ex.message.contains('The post must not be without a tags')
    }
}
