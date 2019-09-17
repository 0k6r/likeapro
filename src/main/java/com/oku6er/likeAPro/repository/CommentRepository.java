package com.oku6er.likeAPro.repository;

import com.oku6er.likeAPro.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
