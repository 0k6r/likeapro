package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.model.Tag;

import java.util.List;

public interface ITagService {
    List<Tag> findAll();
    void save(final Tag tag);
}
