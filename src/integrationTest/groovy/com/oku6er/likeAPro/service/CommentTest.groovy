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

//
//    def "container started"() {
//        given: "a jdbc connection"
//        HikariConfig hikariConfig = new HikariConfig()
//        hikariConfig.setJdbcUrl(postgreSQLContainer.jdbcUrl)
//        hikariConfig.setUsername("sa")
//        hikariConfig.setPassword("sa")
//        HikariDataSource ds = new HikariDataSource(hikariConfig)
//
//        when: "querying the database"
//        Statement statement = ds.getConnection().createStatement()
//        statement.execute("SELECT 1")
//        ResultSet resultSet = statement.getResultSet()
//        resultSet.next()
//
//        then: "result is returned"
//        int resultSetInt = resultSet.getInt(1)
//        resultSetInt == 1
//    }
}
