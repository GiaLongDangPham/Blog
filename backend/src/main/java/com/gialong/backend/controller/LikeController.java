package com.gialong.backend.controller;


import com.gialong.backend.dto.LikeDTO;
import com.gialong.backend.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<LikeDTO> toggleLike(@PathVariable UUID id) {
        LikeDTO like = likeService.toggleLike(id);
        return new ResponseEntity<>(like, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Boolean> isLiked(@PathVariable UUID id) {
        Boolean isLiked = likeService.isLiked(id);
        return ResponseEntity.ok(isLiked);
    }

    @GetMapping("/{id}/count-like")
    public ResponseEntity<Long> countLikes(@PathVariable UUID id) {
        Long count = likeService.countLikes(id);
        return ResponseEntity.ok(count);
    }
}