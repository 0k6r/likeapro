package com.oku6er.likeAPro.model

import com.oku6er.likeAPro.model.post.Post
import com.oku6er.likeAPro.model.post.Text
import spock.lang.Shared
import spock.lang.Specification

import javax.validation.Validation
import javax.validation.Validator

class PostModelVSpec extends Specification {

    @Shared
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator()

    Post post = new Post()

    def setup() {
        post.addTag(new Tag('Java'))
    }

    def 'If create a post without title'() {
        given: 'post without title'
        post.setSlug('java').setText(new Text())

        when: 'validate post'
        def violations = validator.validate(post)

        then: 'throw exception'
        violations.size() == 1
        violations.find { v -> v.message == 'The title must not be null' }
    }

    def 'If create a post without slug'() {
        given: 'post without slug'
        post.setTitle('Java').setText(new Text())

        when: 'validate post'
        def violations = validator.validate(post)

        then: 'throw exception'
        violations.size() == 1
        violations.find { v -> v.message == 'The slug must not be null' }
    }

    def 'If create a post where slug has a length more 50 characters'() {
        given: 'post where slug has a length more 50 characters'
        def longSlug = 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.'
        post.setTitle('Java').setSlug(longSlug).setText(new Text())

        when: 'validate post'
        def violations = validator.validate(post)

        then: 'throw exception'
        violations.size() == 1
        violations.find { v -> v.message == 'The slug has a max length with 50 characters' }
    }


    def 'If create a post without text'() {
        given: 'post without slug'
        post.setTitle('Java').setSlug('java')

        when: 'validate post'
        def violations = validator.validate(post)

        then: 'throw exception'
        violations.size() == 1
        violations.find { v -> v.message == 'The text must not be null' }
    }

    def 'If create a post where vote less 0'() {
        given: 'post where vote less 0'
        post.setTitle('Java').setSlug('java').setText(new Text()).setVote(-1)

        when: 'validate post'
        def violations = validator.validate(post)

        then: 'throw exception'
        violations.size() == 1
        violations.find { v -> v.message == 'The vote has a min length with 0' }
    }

    def 'If create a post without tags'() {
        given: 'post without tags'
        def post = new Post().setTitle('Java').setSlug('java').setText(new Text())

        when: 'validate post'
        def violations = validator.validate(post)

        then: 'throw exception'
        violations.size() == 1
        violations.find { v -> v.message == 'The post must not be without a tags' }
    }
}
