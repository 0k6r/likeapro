package com.oku6er.likeAPro.controller;

import com.oku6er.likeAPro.model.Tag;
import com.oku6er.likeAPro.service.ITagService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagController {
    private final ITagService tagService;

    @GetMapping
    public ResponseEntity<Set<Tag>> getAll() {
        return ResponseEntity.ok(tagService.findAll());
    }
}
