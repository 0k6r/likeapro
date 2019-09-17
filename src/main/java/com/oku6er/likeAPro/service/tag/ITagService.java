package com.oku6er.likeAPro.service.tag;

import com.oku6er.likeAPro.model.Tag;

import java.util.List;

public interface ITagService {
    List<Tag> findAll();
    Tag save(final Tag tag);
    Tag getById(Long id);
}
