package com.oku6er.likeAPro.service

import com.oku6er.likeAPro.BaseIntegrationTest
import com.oku6er.likeAPro.service.post.IPostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ComponentScan(basePackages = "com.oku6er.likeAPro.service")
class CommentTest extends BaseIntegrationTest {

    @Autowired
    @Qualifier("PostService")
    IPostService postService

    def "comment service not null"() {
        when: 'inject service'
        postService.findAll()
        then: 'inspecting the contents'
        postService != null
    }
}
