package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.model.Tag;

import java.util.Set;

public class TagService implements ITagService {

    @Override
    public Set<Tag> findAll() {
        return null;
    }

    @Override
    public void save(Tag tag) {
        throw new NullPointerException();
    }
}
