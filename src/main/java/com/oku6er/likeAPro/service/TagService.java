package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.model.Tag;
import com.oku6er.likeAPro.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.oku6er.likeAPro.exception.NotFoundException;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TagService implements ITagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> findAll() {
        return  tagRepository.findAll();
    }

    @Override
    public Tag save(final Tag tag) {
        return tagRepository.saveAndFlush(tag);
    }

    @Override
    public Tag getById(Long id) {
        final Tag one = tagRepository.getOne(id);
        if (one == null) {
            throw new NotFoundException("Tag with id: '" + id + "' not found");
        }
        return one;
    }
}
