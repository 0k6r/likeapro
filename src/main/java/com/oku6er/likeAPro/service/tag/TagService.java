package com.oku6er.likeAPro.service.tag;

import com.oku6er.likeAPro.exception.NotFoundException;
import com.oku6er.likeAPro.model.Tag;
import com.oku6er.likeAPro.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional
@AllArgsConstructor
public class TagService implements ITagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag save(final Tag tag) {
        return tagRepository.saveAndFlush(tag);
    }

    @Override
    public Tag getById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tag with id: '" + id + "' not found"));
    }
}
