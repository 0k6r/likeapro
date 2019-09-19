package com.oku6er.likeAPro.service.comment;

import com.oku6er.likeAPro.model.Comment;
import com.oku6er.likeAPro.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service("commentService")
@Transactional
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment save(final Comment comment) {
        return commentRepository.saveAndFlush(comment);
    }
}
