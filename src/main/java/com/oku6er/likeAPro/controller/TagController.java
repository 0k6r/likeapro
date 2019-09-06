package com.oku6er.likeAPro.controller;

import com.oku6er.likeAPro.model.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @GetMapping
    public ResponseEntity<List<Tag>> getAll() {
        return ResponseEntity.ok(new ArrayList<>());
    }
}
