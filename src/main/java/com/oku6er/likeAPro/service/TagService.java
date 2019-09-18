package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.exception.NotFoundException;
import com.oku6er.likeAPro.model.Tag;
import com.oku6er.likeAPro.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Tag save(final Tag tag) {
        return tagRepository.saveAndFlush(tag);
    }

    public Tag getById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tag with id: '" + id + "' not found"));
    }
}
