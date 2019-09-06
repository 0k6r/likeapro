package com.oku6er.likeAPro.controller;

import com.oku6er.likeAPro.model.post.Post;
import com.oku6er.likeAPro.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final IPostService postService;

    @Autowired
    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Post> create(@RequestBody Post post) {
        return new ResponseEntity<>(postService.save(post), HttpStatus.CREATED);
    }
}
