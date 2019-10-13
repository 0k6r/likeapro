package com.oku6er.likeAPro.service

import com.oku6er.likeAPro.BaseIntegrationTest
import com.oku6er.likeAPro.service.post.IPostService
import org.springframework.beans.factory.annotation.Autowired

class CommentTest extends BaseIntegrationTest {

    @Autowired
    IPostService commentService

    def "comment service not null"() {
        when: 'inject service'
        commentService.findAll()
        then: 'inspecting the contents'
        commentService != null
    }
}
