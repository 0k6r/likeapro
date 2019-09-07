package com.oku6er.likeAPro.service;

import com.oku6er.likeAPro.model.Tag;
import com.oku6er.likeAPro.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class TagService implements ITagService {

    private final TagRepository tagRepository;

    @Override
    public Set<Tag> findAll() {
        return (Set<Tag>) tagRepository.findAll();
    }

    @Override
    public void save(final Tag tag) {
        tagRepository.save(tag);
    }
}
