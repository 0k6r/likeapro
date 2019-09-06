package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.model.Tag;

import java.util.Set;

public interface ITagService {

    Set<Tag> findAll();

    void save(final Tag tag);
}
